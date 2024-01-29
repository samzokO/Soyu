package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemCategories;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@Getter
@ToString
public class ItemCreateRequest {
//  Long memberId;

  @NotNull
  private String title;

  @NotNull
  private String content;

  @NotNull
  private Integer price;

  @NotNull
  private ItemCategories itemCategories;

}
