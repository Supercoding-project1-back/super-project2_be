package com.example.superproject1.web.dto.item;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponse {
    private Long id;
    private String fileName;
    private int fileSize;
    private String fileExtension;
}