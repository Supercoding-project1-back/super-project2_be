package com.example.superproject1.web.controller.item;

import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import com.example.superproject1.service.item.ItemService;
import com.example.superproject1.web.FindUserByToken;
import com.example.superproject1.web.dto.item.ItemRequest;
import com.example.superproject1.web.dto.item.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final FindUserByToken findUserByToken;

    @Operation(summary = "모든 Items 검색", description = "모든 아이템의 리스트를 가져옵니다.")
    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAllItems(@Parameter(description = "페이지네이션 정보") Pageable pageable) {
        return ResponseEntity.ok(itemService.getAllItems(pageable));
    }

    @Operation(summary = "아이디로 Item 검색", description = "아이템의 id값을 바탕으로 아이템을 가져옵니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@Parameter(description = "경로 변수로 입력된 id값") @PathVariable Long id) {
        Optional<ItemResponse> itemResponse = itemService.getItemById(id);
        return itemResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "새로운 Item 생성", description = "새로운 Item을 생성한다.\n file1과 file2는 item의 이미지 사진으로 MultipartFile 타입이다.")
    @ApiResponse(
            responseCode = "201",
            description = "새로운 Item 생성 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponse.class)
            ))
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                           description = "새로 생성할 Item 정보",
                                                           required = true,
                                                           content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = ItemRequest.class))
                                                   ) ItemRequest itemRequest) {
        ItemResponse response = itemService.createItem(itemRequest, findUserByToken.findUser(customUserDetails));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "기존 Item 정보 수정", description = "해당 id를 가진 기존의 Item의 정보를 업데이트한다. \n file1과 file2는 item의 이미지 사진으로 MultipartFile 타입이다")
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @Parameter(description = "경로 변수로 입력된 id값") @PathVariable Long id,
                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                           description = "수정할 Item 정보",
                                                           required = true,
                                                           content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = ItemRequest.class))
                                                   ) ItemRequest itemRequest) {
        ItemResponse response = itemService.updateItem(id, itemRequest, findUserByToken.findUser(customUserDetails));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "유저가 팔고 있는 Item 조회", description = "유저가 팔고 있는 Item의 정보를 리스트를 페이지네이션해서 전달한다.")
    @GetMapping("/user")
    public ResponseEntity<Page<ItemResponse>> getItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                @Parameter(description = "페이지네이션 정보") Pageable pageable) {
        Page<ItemResponse> response = itemService.getAllItemsByUser(findUserByToken.findUser(customUserDetails), pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Item 삭제", description = "해당 id를 가진 Item을 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@Parameter(description = "경로 변수로 입력된 id값") @PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}