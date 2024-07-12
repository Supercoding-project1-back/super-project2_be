package com.example.superproject1.web.controller.cart;

import com.example.superproject1.repository.cart.Cart;
import com.example.superproject1.repository.cart.CartItem;
import com.example.superproject1.repository.cart.CartItemRepository;
import com.example.superproject1.repository.cart.CartRepository;
import com.example.superproject1.repository.item.Item;
import com.example.superproject1.repository.item.ItemRepository;
import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import com.example.superproject1.service.cart.CartService;
import com.example.superproject1.service.exceptions.NotAcceptableException;
import com.example.superproject1.web.dto.cart.CartItemRequest;
import com.example.superproject1.web.dto.cart.CartAllSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니에 물품 추가", description = "아이템의 id와 사용자가 입력한 물품 수량으로 추가")
    @PostMapping
    public ResponseEntity<CartItemRequest> addCartItem(@AuthenticationPrincipal CustomUserDetails customUserDetails , @Parameter(name="itemId", description = "장바구니에 추가할 아이템 id, 수량", example = "{'itmeId': 1, 'count': 5}") @RequestBody CartItemRequest cartItemRequest) {
        System.out.println(customUserDetails.toString());
        Long userId = customUserDetails.getId();
        CartItemRequest response = cartService.addCartItem(userId, cartItemRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장바구니 조회", description = "user의 id로 장바구니 물품 조회")
    @GetMapping
    public ResponseEntity<List<CartAllSearchResponse>> findCartItems(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = customUserDetails.getId();
        List<CartAllSearchResponse> responseList = cartService.findCartItems(userId);

        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "장바구니 물품 수량 수정", description = "장바구니의 아이템 수량을 userId와 itemId로 찾아 수정")
    @PatchMapping
    public ResponseEntity<CartItemRequest> updateCartItem(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Parameter(name="itemId", description = "수정할 아이템 id, 수량", example = "{'itmeId': 1, 'count': 3}") @RequestBody CartItemRequest cartItemRequest){
        Long userId = customUserDetails.getId();
        CartItemRequest response = cartService.updateCartItem(userId, cartItemRequest);

        return ResponseEntity.ok(response);
    }
}