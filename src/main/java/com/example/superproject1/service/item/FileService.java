package com.example.superproject1.service.item;

import com.example.superproject1.repository.item.File;
import com.example.superproject1.repository.item.FileRepository;
import com.example.superproject1.repository.item.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private AtomicInteger fileCount;

    // 파일 디렉터리
    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    // 값 카운팅
    @PostConstruct
    public void init() {
        this.fileCount = new AtomicInteger(0); // 초기값 설정
    }

    // 파일 업로드 및 DB 생성
    public File createFile(MultipartFile multipartFile, Item item) {
        // 이미지 파일이 없을 경을 경우
        if (multipartFile.isEmpty()) new IllegalArgumentException("File is empty");

        File image = new File();
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // 디렉터리 존재 확인

            // 파일 정보
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String fileExtension = getFileExtension(fileName);
            long fileSize = multipartFile.getSize();
            String fileUrl = "/images/" + fileName;

            // 이미지 파일 저장
            Path path = Paths.get(UPLOAD_DIR + fileName);
            if (Files.exists(path)) { // 이미 같은 이름의 파일이 존재하는 경우
                fileName = fileCount.incrementAndGet() + "_" + fileName;
                fileUrl = "/images/" + fileName;
                path = Paths.get(UPLOAD_DIR + fileName);
            }
            Files.write(path, multipartFile.getBytes());

            // 파일 엔티티 생성
            image = new File().builder()
                    .fileName(fileName)
                    .fileSize(fileSize)
                    .fileExtension(fileExtension)
                    .fileUrl(fileUrl)
                    .item(item)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // 파일 확장자
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}