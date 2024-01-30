package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private Integer price;
    private ItemStatus itemStatus;
    private ItemCategories itemCategories;

}
