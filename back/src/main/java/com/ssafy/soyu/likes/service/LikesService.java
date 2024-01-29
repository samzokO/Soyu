package com.ssafy.soyu.likes.service;

import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.likes.repository.LikesRepository;
import com.ssafy.soyu.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
  final private LikesRepository likesRepository;

  public List<Likes> getLikes (Long memberId) {
    return likesRepository.findLikesByMemberId(memberId);
  }


}
