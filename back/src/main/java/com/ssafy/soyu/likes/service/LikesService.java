package com.ssafy.soyu.likes.service;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.likes.repository.LikesRepository;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.service.NoticeService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
  final private LikesRepository likesRepository;
  final private ItemRepository itemRepository;
  final private MemberRepository memberRepository;
  final private NoticeService noticeService;

  public List<Likes> getLikes (Long memberId) {
    return likesRepository.findLikesByMemberId(memberId);
  }

  public void onOff(Long itemId, Long memberId) {
    Item item = itemRepository.getReferenceById(itemId);
    Member member = memberRepository.getReferenceById(memberId);

    // 아이템 Id 와 Member Id 를 사용하여 likes 를 조회해와야 한다
    Likes likes = likesRepository.findLikesByItemAndMember(item, member);

    if (likes != null) {
      likes.changeStatus();
    } else {
      // 찜 된 상태로 생성한다
      likesRepository.save(new Likes(member, item, true));
    }

    //찜이 되면 판매자에게 알림 전송
    if(likesRepository.findLikesByItemAndMember(item, member).getStatus()){
      noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, NoticeType.LIKE));
    };
  }

  public Integer getLikeCountByItemId(Long itemId){
    return likesRepository.countLikeByItemId(itemId);
  }
}
