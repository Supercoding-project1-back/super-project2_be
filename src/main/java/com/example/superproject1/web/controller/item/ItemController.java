package com.example.superproject1.web.controller.item;

import com.example.superproject1.service.item.ItemService;
import com.example.superproject1.web.dto.item.ItemRequest;
import com.example.superproject1.web.dto.item.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

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

    @Operation(summary = "새로운 Item 생성", description = "새로운 Item을 생성한다.")
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@Parameter(description = "새로 생성할 Item 정보") @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.createItem(itemRequest));
    }

    @Operation(summary = "기존 Item 정보 수정", description = "해당 id를 가진 기존의 Item의 정보를 업데이트한다.")
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@Parameter(description = "경로 변수로 입력된 id값") @PathVariable Long id, @Parameter(description = "Item을 업데이트 할 정보") @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.updateItem(id, itemRequest));
    }

    @Operation(summary = "Item 삭제", description = "해당 id를 가진 Item을 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@Parameter(description = "경로 변수로 입력된 id값") @PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}