package com.ssafy.soyu.history.dto.response;

import com.ssafy.soyu.image.dto.response.ImageResponse;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 판매 내역 반환 DTO
 * @itemId = 판매 물품의 식별자
 * @price = 물품의 가격
 * @filepath = 이미지 저장 경로
 * @filename = 이미지 저장명
 * @regDate = 물품 판매 등록일
 * @itemStatus = 아이템 현재 상태 (ONLINE, DP, RESERVE, COMP, RETURN)
 */
@Schema(description = "판매 내역 조회 응답 DTO")
@Data
public class SaleResponseDto {

  @Schema(description = "물품 ID")
  private Long itemId;

  @Schema(description = "물품 이름")
  private String title;

  @Schema(description = "가격 ID")
  private int price;

  private List<ImageResponse> imageResponses;

  @Schema(description = "물품 상태")
  private ItemStatus itemStatus;

  @Schema(description = "물품 등록일")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime regDate;

  @Schema(description = "찜 개수")
  private Integer likeCounts;

  public SaleResponseDto(Item i, List<ImageResponse> images, Integer likeCounts) {
    this.itemId = i.getId();
    this.title = i.getTitle();
    this.price = i.getPrice();
    this.regDate = i.getRegDate();
    this.itemStatus = i.getItemStatus();
    this.imageResponses = images;
    this.likeCounts = likeCounts;
  }
}
