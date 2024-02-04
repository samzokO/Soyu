package com.ssafy.soyu.item.dto.response;

import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "물품 조회 리스트 결과 응답 DTO")
@AllArgsConstructor
@Data
public class ItemListResponse {
  @Schema(description = "물품 ID")
  Long itemId;

  @Schema(description = "판매자 ID")
  Long memberId;

  @Schema(description = "닉네임")
  String nickname;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "물품 등록일")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime regDate;

  @Schema(description = "가격")
  private Integer price;

  @Schema(description = "물품 상태")
  private ItemStatus itemStatus;

  @Schema(description = "카테고리")
  private ItemCategories itemCategories;

  @Schema(description = "대표 사진")
  private List<Image> image;
}
