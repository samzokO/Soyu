package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.locker.entity.Locker;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Schema(description = "보관함 정보 응답 DTO")
@Data
public class FindResponseDto {

  @Schema(description = "보관함 ID")
  private Long lockerId;

  @Schema(description = "물품 ID")
  private Long itemId;

  @Schema(description = "보관함 상태")
  private String status;

  @Schema(description = "물품 제목")
  private String title;

  @Schema(description = "물품 등록일")
  private LocalDateTime regDate;

  @Schema(description = "물품 가격")
  private Integer price;

  @Schema(description = "물품 카테고리")
  private ItemCategories categories;

  public FindResponseDto(Locker l){
    this.lockerId = l.getId();
    this.status = l.getStatus().toString();
    if(l.getItem() != null){
      this.itemId = l.getItem().getId();
      this.title = l.getItem().getTitle();
      this.regDate = l.getItem().getRegDate();
      this.price = l.getItem().getPrice();
      this.categories = l.getItem().getItemCategories();
    }
  }
}
