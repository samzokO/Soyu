package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 중복 DTO가 있는듯 정리 필요
 */
@Schema(description = "물품 정보 응답 DTO")
@Data
@Builder
public class ItemResponse {

    @Schema(description = "물품 ID")
    private Long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "설명")
    private String content;

    @Schema(description = "등록일")
    private LocalDateTime regDate;

    @Schema(description = "가격")
    private Integer price;

    @Schema(description = "물품 상태")
    private ItemStatus itemStatus;

    @Schema(description = "카테고리")
    private ItemCategories itemCategories;
}
