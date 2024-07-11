package com.example.superproject1.web.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponse {
    @Schema(description = "이미지 파일의 고유한 id")
    private Long id;
    @Schema(description = "이미지 파일 이름")
    private String fileName;
    @Schema(description = "이미지 파일 사이즈(바이트)")
    private int fileSize;
    @Schema(description = "이미지 파일 확장명")
    private String fileExtension;
}