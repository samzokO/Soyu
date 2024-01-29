package com.ssafy.soyu.likes.dto.response;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LikesResponse {
  private Item item;
  private boolean status;
}
