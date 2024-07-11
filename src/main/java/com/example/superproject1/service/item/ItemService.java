package com.example.superproject1.service.item;

import com.example.superproject1.repository.item.File;
import com.example.superproject1.repository.item.Item;
import com.example.superproject1.repository.item.ItemRepository;
import com.example.superproject1.web.dto.item.FileRequest;
import com.example.superproject1.web.dto.item.FileResponse;
import com.example.superproject1.web.dto.item.ItemRequest;
import com.example.superproject1.web.dto.item.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<ItemResponse> getAllItems(Pageable pageable) {
        return itemRepository.findAllByCountGreaterThan(0, pageable)
                .map(this::convertToItemResponse);
    }

    public Optional<ItemResponse> getItemById(Long id) {
        return itemRepository.findById(id)
                .map(this::convertToItemResponse);
    }

    public ItemResponse createItem(ItemRequest itemRequest) {
        Item item = convertToItemEntity(itemRequest);
        itemRepository.save(item);
        return convertToItemResponse(item);
    }

    public ItemResponse updateItem(Long id, ItemRequest itemRequest) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            updateItemFromRequest(item, itemRequest);
            itemRepository.save(item);
            return convertToItemResponse(item);
        } else {
            throw new IllegalArgumentException("아이템 업데이트 실패: 아이템을 찾을 수 없습니다.");
        }
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    private ItemResponse convertToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .count(item.getCount())
                .price(item.getPrice())
                .size(item.getSize())
                .careGuide(item.getCareGuide())
                .name(item.getName())
                .description(item.getDescription())
                .category(item.getCategory())
                .deliveryFee(item.getDeliveryFee())
                .build();
    }

    private Item convertToItemEntity(ItemRequest itemRequest) {
        return Item.builder()
                .count(itemRequest.getCount())
                .price(itemRequest.getPrice())
                .size(itemRequest.getSize())
                .careGuide(itemRequest.getCareGuide())
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .category(itemRequest.getCategory())
                .deliveryFee(itemRequest.getDeliveryFee())
                .build();
    }

    private void updateItemFromRequest(Item item, ItemRequest itemRequest) {
        item.setCount(itemRequest.getCount());
        item.setPrice(itemRequest.getPrice());
        item.setSize(itemRequest.getSize());
        item.setCareGuide(itemRequest.getCareGuide());
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setCategory(itemRequest.getCategory());
        item.setDeliveryFee(itemRequest.getDeliveryFee());
    }

    private FileResponse convertToFileResponse(File file) {
        return FileResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileExtension(file.getFileExtension())
                .build();
    }

    private File convertToFileEntity(FileRequest fileRequest) {
        return File.builder()
                .fileName(fileRequest.getFileName())
                .fileSize(fileRequest.getFileSize())
                .fileExtension(fileRequest.getFileExtension())
                .build();
    }
}
