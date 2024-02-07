package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.locker.entity.Locker;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "보관함 정보 응답 DTO")
@Data
@RequiredArgsConstructor
public class LockerResponseDto {

  @Schema(description = "보관함 ID")
  private Long lockerId;

  @Schema(description = "보관함 위치")
  private Integer lockerLocation;

  @Schema(description = "보관함 상태")
  private String status;

  @Schema(description = "물품 ID")
  private Long itemId;

  @Schema(description = "물품 제목")
  private String title;

  @Schema(description = "물품 등록일")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime regDate;

  @Schema(description = "물품 가격")
  private Integer price;

  @Schema(description = "물품 카테고리")
  private ItemCategories categories;

  @Schema(description = "로그인 한 유저의 찜 여부")
  private Boolean isLike;

  @Schema(description = "물품의 총 찜 개수")
  private Integer likeCount;

  @Schema(description = "물품 이미지")
  private List<Image> itemImage;

  public LockerResponseDto(Locker l) {
    this.lockerId = l.getId();
    this.status = l.getStatus().toString();
    this.lockerLocation = l.getLockerNum();
  }

  public LockerResponseDto(Locker l, Boolean isLike, Integer likeCount) {
    this.lockerId = l.getId();
    this.lockerLocation = l.getLockerNum();
    this.status = l.getStatus().toString();
    this.itemId = l.getItem().getId();
    this.itemImage = l.getItem().getImage();
    this.title = l.getItem().getTitle();
    this.regDate = l.getItem().getRegDate();
    this.price = l.getItem().getPrice();
    this.categories = l.getItem().getItemCategories();
    this.isLike = isLike;
    this.likeCount = likeCount;
  }
}
