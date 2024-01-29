package com.ssafy.soyu.history.dto.response;

import com.ssafy.soyu.file.File;
import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.item.entity.ItemStatus;
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
@Data
public class PurchaseResponseDto {
  private Long itemId;
  private int price;
  private String sellerNickName;
  private String filepath;
  private String fileName;
  private ItemStatus itemStatus;
  LocalDateTime regDate;

  public PurchaseResponseDto(History h, File f) {
    this.itemId = h.getItem().getId();
    this.price = h.getItem().getPrice();
    this.sellerNickName = h.getMember().getNickName();
    this.regDate = h.getItem().getRegDate();
    this.itemStatus = h.getItem().getItemStatus();
    this.fileName = f.getSaveName();
    this.filepath = f.getSavePath();}
}
