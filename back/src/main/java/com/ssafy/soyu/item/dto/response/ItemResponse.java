package com.ssafy.soyu.item.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "물품 조회 결과 응답 DTO")
@AllArgsConstructor
@Data
public class ItemResponse {

  @Schema(description = "물품 ID")
  Long itemId;

  @Schema(description = "판매자 ID?")
  Long memberId;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "설명")
  private String content;

  @Schema(description = "물품 등록일")
  private LocalDateTime regDate;

  @Schema(description = "가격")
  private Integer price;

  @Schema(description = "물품 상태")
  private ItemStatus itemStatus;

  @Schema(description = "카테고리")
  private ItemCategories itemCategories;
}
