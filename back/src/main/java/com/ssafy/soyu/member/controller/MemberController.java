package com.ssafy.soyu.member.controller;

import static com.ssafy.soyu.profileImage.dto.response.ProfileImageResponse.getProfileImageResponse;
import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.member.dto.request.AccountDto;
import com.ssafy.soyu.member.dto.request.MemberRequest;
import com.ssafy.soyu.member.dto.response.MemberDetailResponse;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member 컨트롤러", description = "Member API 입니다.")
public class MemberController {
    private final MemberService memberService;

    //토큰 재발급 == filter 안거침 == 헤더에서 직접 가져와야함.
    @PostMapping("/token")
    @Operation(summary = "토큰 재발급", description = "Header의 bearerToken을 이용해 토큰을 재발급합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
        @ApiResponse(responseCode = "400", description = "토큰 재발급 실패")
    })
    public ResponseEntity<?> recreateToken
            (@RequestHeader(value = "Authorization", required = false) String bearerToken) {
        String refreshToken = memberService.getToken(bearerToken);

        //리프레시토큰 -> 토큰 검증 -> 토큰 재발급 -> db저장
        TokenResponse token = memberService.recreateToken(refreshToken);
        return getResponseEntity(SuccessCode.OK, token);
    }

    @PostMapping()
    @Operation(summary = "로그아웃", description = "사용자 ID(In Header)를 이용해 로그아웃합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    public ResponseEntity<?> logout(HttpServletRequest request){
        Long memberId = (Long) request.getAttribute("memberId");
        memberService.logout(memberId);
        return getResponseEntity(SuccessCode.OK);
    }

    @PatchMapping("/account")
    @Operation(summary = "계좌 등록", description = "AccountDto를 이용해 계좌를 등록합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "계좌 등록 성공"),
        @ApiResponse(responseCode = "400", description = "계좌 등록 실패")
    })
    public ResponseEntity<?> registrationAccount(@Validated @RequestBody AccountDto accountDto, BindingResult bindingResult, HttpServletRequest request){
        // HttpServletRequest에서  멤버 id 가져오기
        Long memberId = (Long) request.getAttribute("memberId");
        if (bindingResult.hasErrors())
            throw new CustomException(ErrorCode.INPUT_EXCEPTION);
        memberService.updateAccount(memberId, accountDto);

        return getResponseEntity(SuccessCode.OK);
    }

    @GetMapping("/account")
    @Operation(summary = "계좌 조회", description = "사용자 ID(In Header)를 이용해 계좌를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계좌 조회 성공"),
            @ApiResponse(responseCode = "404", description = "계좌 조회 실패")
    })
    public ResponseEntity<?> getAccount(HttpServletRequest request){
        // HttpServletRequest에서  멤버 id 가져오기
        Long memberId = (Long) request.getAttribute("memberId");
        return getResponseEntity(SuccessCode.OK, memberService.getAccount(memberId));
    }

    @DeleteMapping("/account")
    @Operation(summary = "등록된 계좌 삭제", description = "사용자 ID(In Header)를 이용해 등록된 계좌 정보를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "등록 계좌 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "등록 계좌 삭제 실패")
    })
    public ResponseEntity<?> deleteAccount(HttpServletRequest request){
        Long memberId = (Long) request.getAttribute("memberId");
        if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        memberService.deleteAccount(memberId);

        return getResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "사용자 ID(In Header)를 이용해 회원 정보를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
        @ApiResponse(responseCode = "400", description = "회원탈퇴 실패")
    })
    public ResponseEntity<?> deleteMember(HttpServletRequest request){
        Long memberId = (Long) request.getAttribute("memberId");
        memberService.deleteMember(memberId);
        return getResponseEntity(SuccessCode.OK);
    }

    @PatchMapping("/nickname")
    @Operation(summary = "별명 수정", description = "nickName과 사용자 ID를 이용해 중복 확인 후 별명을 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "별명 수정 성공"),
        @ApiResponse(responseCode = "400", description = "별명 수정 실패")
    })
    public ResponseEntity<?> makeNickname(@RequestParam("nickName") String nickName, HttpServletRequest request){
        Long memberId = (Long) request.getAttribute("memberId");
        memberService.checkNickName(memberId, nickName);
        return getResponseEntity(SuccessCode.OK);
    }

    // 회원 가입 이후 개인정보 수정으로 나머지 정보 입력
    @PatchMapping("")
    public ResponseEntity<?> updateMember(HttpServletRequest request,
        @RequestPart(value = "image", required = false) MultipartFile file,
        @RequestPart(value = "memberCreateRequest") MemberRequest memberRequest, BindingResult bindingResult) throws IOException {
        Long memberId = (Long) request.getAttribute("memberId");

        if (file == null) {
            throw new CustomException(ErrorCode.NO_HAVE_IMAGE);
        }
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INPUT_EXCEPTION);
        }

        memberService.updateMember(memberRequest, memberId, file);
        return getResponseEntity(SuccessCode.OK);
    }

    // 내정보
    @GetMapping("")
    public ResponseEntity<?> getMember(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");

        Member member = memberService.getMember(memberId);
        MemberDetailResponse memberDetailResponse = new MemberDetailResponse(member.getId(), getProfileImageResponse(member.getProfileImage()),
            member.getSnsId(), member.getEmail(), member.getNickName(), member.getName(), member.getMobile(), member.getBank_name(), member.getAccount_number(), member.getIsWithdraw());
        return getResponseEntity(SuccessCode.OK, memberDetailResponse);
    }
}