package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemCategories;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

/**
 * 물품 생성 요청 Dto<br/>
 * title - 제목<br/>
 * content - 설명<br/>
 * price - 가격<br/>
 * itemCategories - 카테고리
 */
@Schema(description = "물품 생성 요청 DTO")
@Getter
@ToString
@Setter
public class ItemCreateRequest {

  @Schema(description = "제목")
  @NotNull
  private String title;

  @Schema(description = "설명")
  @NotNull
  private String content;

  @Schema(description = "가격")
  @NotNull
  private Integer price;

  @Schema(description = "카테고리")
  @NotNull
  private ItemCategories itemCategories;

}
