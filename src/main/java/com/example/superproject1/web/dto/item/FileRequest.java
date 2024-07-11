package com.example.superproject1.web.dto.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {
    private String fileName;
    private int fileSize;
    private String fileExtension;
}