package com.ssafy.soyu.member.service;

import com.ssafy.soyu.profileImage.ProfileImage;
import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.member.dto.request.MemberNickNameRequest;
import com.ssafy.soyu.member.dto.response.AccountResponse;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.dto.request.AccountDto;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.jwt.entity.RefreshToken;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.jwt.repository.AuthRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ItemRepository itemRepository;
    private final HistoryRepository historyRepository;

    @Value("${file.path.upload-images}")
    String uploadImagePath;

    @Transactional
    public TokenResponse login(Member member) {
        //accessToken, refreshToken 발급
        TokenResponse token = jwtTokenProvider.createToken(member.getId());

        // DB에서 해당 member의 refreshToken을 새 토큰으로 업데이트
        int updatedRows = authRepository.updateRefreshTokenFindByMember(member.getId(),
            token.getRefreshToken());

        // 업데이트된 행이 없다면 새 refreshToken 생성 및 저장 == 회원가입한 유저
        if (updatedRows == 0) {
            RefreshToken newRefreshToken = RefreshToken.createRefreshToken(member,
                token.getRefreshToken());
            authRepository.save(newRefreshToken);
        }
        return token;
    }

    public Member signUp(Member member) {
        member.updateNickName(member.getName());
        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public TokenResponse recreateToken(String refreshToken) {
        //유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken))
            return null;

        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);

        // 새로운 토큰 생성
        TokenResponse token = jwtTokenProvider.createToken(memberId);

        // DB에서 refreshToken을 새 토큰으로 업데이트
        int updatedRows = authRepository.updateRefreshTokenFindByToken(refreshToken,
            token.getRefreshToken());

        // 업데이트된 행이 없다면, 유효하지 않은 refreshToken
        if (updatedRows == 0) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return token;
    }

    public String getToken(String bearerToken) {
        String token = null;
        if (bearerToken.startsWith("Bearer"))
            token = bearerToken.substring(7);
        return token;
    }

    @Transactional
    public void updateAccount(Long memberId, AccountDto accountDto) {
        memberRepository.updateAccount(memberId, accountDto.getBankName(),
            accountDto.getAccountNumber());
    }

    @Transactional
    public void deleteAccount(Long memberId) {
        memberRepository.updateAccount(memberId, null, null);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        // 판매 완료되지 않은 물건이 있으면 안됨
        List<Item> itemList = itemRepository.findByMemberId(memberId);
        for (Item item : itemList) {
            if (item.getItemStatus() != ItemStatus.from("SOLD")) {
                throw new CustomException(ErrorCode.HAS_ACTIVE_ITEM);
            }
        }

        //구매자인 경우 예약된 물건이 있으면 안됨
        List<History> history = historyRepository.findByMemberId(memberId);
        for(History historyValue : history){
            if (historyValue.getItem().getItemStatus() == ItemStatus.TRADE_RESERVE) {
                throw new CustomException(ErrorCode.HAS_ACTIVE_ITEM);
            }
        }

        memberRepository.updateWithDraw(memberId);
    }

    @Transactional
    public void logout(Long memberId) {
        authRepository.updateRefreshTokenFindByMember(memberId, null);
    }

    @Transactional
    public void checkNickName(Long memberId, String nickName) {
        Optional<Member> duplicateMember = memberRepository.findByNickName(nickName);
        if (duplicateMember.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NICKNAME);
        }

        memberRepository.updateNickName(memberId, nickName);
    }

    public AccountResponse getAccount(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        if (member.getBank_name() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT);
        }
        return AccountResponse.builder()
            .accountNumber(member.getAccount_number())
            .bankName(member.getBank_name())
            .build();
    }

    @Transactional
    public void updateProfile(Long memberId, MultipartFile file)
        throws IOException {

        ProfileImage profileImage = new ProfileImage();
        if (file != null) {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = uploadImagePath + java.io.File.separator + today;

            // 위에서 제작한 경로로 디렉터리를 만든다 (날짜별)
            File folder = new File(saveFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null) {
                String saveFileName = UUID.randomUUID().toString() // UUID 사용으로 고유한 파일의 이름 지정
                    + originalFileName.substring(originalFileName.lastIndexOf('.')); // 확장자 확인

                profileImage.makeImage(today, originalFileName, saveFileName);

                file.transferTo(new File(folder, saveFileName)); // 해당 folder에 해당 이름의 파일로 이동한다
            }
            // images -> 저장해야 한다

        }
        Member member = memberRepository.findById(memberId).get();

        // 더티체킹
        member.updateProfile(profileImage);
    }

    @Transactional
    public void updateNickName(MemberNickNameRequest memberNickNameRequest, Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        // 더티체킹
        member.updateNickName(memberNickNameRequest.getNickName());
    }

    public Member getMember(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }
}