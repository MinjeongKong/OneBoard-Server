package com.connect.oneboardserver.service.storage;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@NoArgsConstructor
@Service
public class FileStorageService implements StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String basePath;

    private String uploadPath = "/upload/";

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(basePath + uploadPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to create directory : " + basePath + uploadPath);
        }
    }

    @Override
    public void store(MultipartFile file) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("File is empty");
            }
            Path dirPath = Paths.get(basePath + uploadPath);
            if (!Files.exists(dirPath)) {
                init();
            }
            String filePath = dirPath + "/" + file.getOriginalFilename() + "_" + LocalDateTime.now();
            System.out.println(filePath);
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            throw new Exception("Fail to upload file");
        }
    }

    @Override
    public void load() {

    }

    @Override
    public void delete() {

    }
}
