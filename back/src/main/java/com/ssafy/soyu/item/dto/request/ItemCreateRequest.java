package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemCategories;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

/**
 * 물품 생성 요청 Dto<br/>
 * title - 제목<br/>
 * content - 설명<br/>
 * price - 가격<br/>
 * itemCategories - 카테고리
 */
@Getter
@ToString
public class ItemCreateRequest {
//  Long memberId;

  @NotNull
  private String title;

  @NotNull
  private String content;

  @NotNull
  private Integer price;

  @NotNull
  private ItemCategories itemCategories;

}
