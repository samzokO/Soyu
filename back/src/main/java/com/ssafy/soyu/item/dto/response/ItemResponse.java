package com.ssafy.soyu.item.dto.response;

import com.ssafy.soyu.image.dto.response.ImageResponse;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "물품 상세 조회 결과 응답 DTO")
@AllArgsConstructor
@Data
public class ItemResponse {

  @Schema(description = "물품 ID")
  Long itemId;

  @Schema(description = "판매자 ID")
  Long memberId;

  @Schema(description = "닉네임")
  String nickname;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "설명")
  private String content;

  @Schema(description = "물품 등록일")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime regDate;

  @Schema(description = "가격")
  private Integer price;

  @Schema(description = "물품 상태")
  private ItemStatus itemStatus;

  @Schema(description = "카테고리")
  private ItemCategories itemCategories;

  @Schema(description = "사진 리스트")
  private List<ImageResponse> imageResponses;

  private Boolean likesStatus;

  public ItemResponse(Long itemId, Long memberId, String nickname, String title, String content,
      LocalDateTime regDate, Integer price, ItemStatus itemStatus, ItemCategories itemCategories,
      List<ImageResponse> imageResponses) {
    this.itemId = itemId;
    this.memberId = memberId;
    this.nickname = nickname;
    this.title = title;
    this.content = content;
    this.regDate = regDate;
    this.price = price;
    this.itemStatus = itemStatus;
    this.itemCategories = itemCategories;
    this.imageResponses = imageResponses;
  }
}
