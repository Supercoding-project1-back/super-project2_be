package com.example.superproject1.web.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemResponse {
    @Schema(description = "물품의 고유한 id")
    private Long id;
    @Schema(description = "물품 개수")
    private int count;
    @Schema(description = "물품 가격")
    private int price;
    @Schema(description = "물품 크기")
    private String size;
    @Schema(description = "케어가이드")
    private String careGuide;
    @Schema(description = "물품명")
    private String name;
    @Schema(description = "물품 설명")
    private String description;
    @Schema(description = "물품 카테고리")
    private String category;
    @Schema(description = "물품 배송비")
    private int deliveryFee;
    @Schema(description = "물품 이미지 파일")
    private List<FileResponse> files;
}