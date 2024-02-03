package com.ssafy.soyu.history.dto.response;

import com.ssafy.soyu.file.ProfileImage;
import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 구매 내역 반환 DTO
 * @itemId = 구매한 물품의 식별자
 * @price = 물품의 가격
 * @sellerNickName = 판매자 닉네임
 * @filepath = 이미지 저장 경로
 * @filename = 이미지 저장명
 * @regDate = 물품 판매 등록일
 * @itemStatus = 아이템 현재 상태 (RESERVE, COMP)
 */
@Schema(description = "구매 내역 조회 응답 DTO")
@Data
public class PurchaseResponseDto {

  @Schema(description = "물품 ID")
  private Long itemId;

  @Schema(description = "가격")
  private int price;

  @Schema(description = "판매자 별명")
  private String sellerNickName;

  @Schema(description = "이미지 저장 경로")
  private String filepath;

  @Schema(description = "이미지 저장명")
  private String fileName;

  @Schema(description = "물품 상태")
  private ItemStatus itemStatus;

  @Schema(description = "물품 등록일")
  LocalDateTime regDate;

  public PurchaseResponseDto(History h, Image i) {
    this.itemId = h.getItem().getId();
    this.price = h.getItem().getPrice();
    this.sellerNickName = h.getMember().getNickName();
    this.regDate = h.getItem().getRegDate();
    this.itemStatus = h.getItem().getItemStatus();
    this.fileName = i.getSaveName();
    this.filepath = i.getSavePath();}
}
