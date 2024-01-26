package com.ssafy.soyu.history.dto.response;

import com.ssafy.soyu.file.File;
import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.item.ItemStatus;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 판매 내역 반환 DTO
 * @itemId = 판매 물품의 식별자
 * @price = 물품의 가격
 * @filepath = 이미지 저장 경로
 * @filename = 이미지 저장명
 * @regDate = 물품 판매 등록일
 * @itemStatus = 아이템 현재 상태 (ONLINE, DP, RESERVE, COMP, RETURN)
 */
@Data
public class SaleResponseDto {
  private Long itemId;
  private int price;
  private String filepath;
  private String fileName;
  private ItemStatus itemStatus;
  private LocalDateTime regDate;

  public SaleResponseDto(Item i, File f) {
    this.itemId = i.getId();
    this.price = i.getPrice();
    this.regDate = i.getRegDate();
    this.itemStatus = i.getItemStatus();
    this.fileName = f.getSaveName();
    this.filepath = f.getSavePath();
  }
}
