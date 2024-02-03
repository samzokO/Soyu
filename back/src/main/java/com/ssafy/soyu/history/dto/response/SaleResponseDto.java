package com.ssafy.soyu.history.dto.response;

import com.ssafy.soyu.file.ProfileImage;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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

  @Schema(description = "가격 ID")
  private int price;

  @Schema(description = "이미지 저장 경로")
  private String filepath;

  @Schema(description = "이미지 저장명")
  private String fileName;

  @Schema(description = "물품 상태")
  private ItemStatus itemStatus;

  @Schema(description = "물품 등록일")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime regDate;

  public SaleResponseDto(Item i, Image m) {
    this.itemId = i.getId();
    this.price = i.getPrice();
    this.regDate = i.getRegDate();
    this.itemStatus = i.getItemStatus();
    this.fileName = m.getSaveName();
    this.filepath = m.getSavePath();
  }
}
