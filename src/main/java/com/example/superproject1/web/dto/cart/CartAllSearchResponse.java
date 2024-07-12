package com.example.superproject1.web.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAllSearchResponse {
    @Schema(description = "물품 아이디")
    private Long itemId;
    @Schema(description = "물품 개수")
    private Integer count;
}
