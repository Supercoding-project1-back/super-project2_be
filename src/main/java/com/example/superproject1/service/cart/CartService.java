package com.example.superproject1.service.cart;

import com.example.superproject1.repository.cart.Cart;
import com.example.superproject1.repository.cart.CartItem;
import com.example.superproject1.repository.cart.CartItemRepository;
import com.example.superproject1.repository.cart.CartRepository;
import com.example.superproject1.repository.item.Item;
import com.example.superproject1.repository.item.ItemRepository;
import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import com.example.superproject1.service.exceptions.NotAcceptableException;
import com.example.superproject1.web.dto.cart.CartAllSearchResponse;
import com.example.superproject1.web.dto.cart.CartItemRequest;
import com.example.superproject1.web.dto.item.ItemRequest;
import com.example.superproject1.web.dto.item.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    public CartItemRequest addCartItem(Long userId ,CartItemRequest cartItemRequest) {
        Cart cart = cartRepository.findByUserId(userId);
        Long itemId = cartItemRequest.getItemId();
        System.out.println(itemId);
        Integer itemPrice = itemRepository.findById(itemId).orElseThrow().getPrice();
        System.out.println(itemPrice);
        Integer userItemCount = cartItemRequest.getCount();

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("error"));
        Integer itemCount = item.getCount();
        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item);

        if(userItemCount > itemCount){
            throw new NotAcceptableException("물건 최대 개수는 " + itemCount + " 입니다.",itemCount.toString());
        }
        if(cartItem == null) {
            CartItem isNullCartItem = CartItem.builder().item(item).cart(cart).count(itemCount).build();
            cartItemRepository.save(isNullCartItem);
        }else{
            Long cartItemId = cartItem.getId();
            cartItem.setCount(userItemCount);
            cartItemRepository.save(cartItem);
        }
        return cartItemRequest;

    }

    public List<CartAllSearchResponse> findCartItems(Long userId){
        Cart cart = cartRepository.findByUserId(userId);
        Long cartId = cart.getId();
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        System.out.println(cartItems);

        return cartItems.stream()
                .map(cartItem -> CartAllSearchResponse.builder()
                        .itemId(cartItem.getItem().getId())
                        .count(cartItem.getCount())
                        .build())
                .collect(Collectors.toList());
    }

    public CartItemRequest updateCartItem(Long userId, CartItemRequest cartItemRequest){
        Cart cart = cartRepository.findByUserId(userId);
        Long cartId = cart.getId();
        Long itemId = cartItemRequest.getItemId();
        System.out.println(itemId);
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("error"));
        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item);
        Long cartItemId = cartItem.getId();
        Integer itemQuantity = cartItemRequest.getCount();

        cartItem.setCount(cartItemRequest.getCount());
        cartItemRepository.save(cartItem);

        return cartItemRequest;


    }
}
