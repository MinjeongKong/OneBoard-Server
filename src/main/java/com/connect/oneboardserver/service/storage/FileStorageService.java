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

@NoArgsConstructor
@Service
public class FileStorageService implements StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String basePath;

    @Override
    public void init(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to create directory : " + path);
        }
    }

    @Override
    public String store(String uploadPath, MultipartFile file) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("File is empty");
            }

            Path dirPath = Paths.get(basePath + uploadPath);
            if (!Files.exists(dirPath)) {
                init(dirPath);
            }

            String filePath = dirPath + "/" + getUploadFileName(file.getOriginalFilename());
            file.transferTo(new File(filePath));

            return filePath;
        } catch (Exception e) {
            throw new Exception("Fail to upload file");
        }
    }

    private String getUploadFileName(String fileFullName) {
        return fileFullName.substring(0, fileFullName.lastIndexOf("."))
                + "_" + System.currentTimeMillis()
                + fileFullName.substring(fileFullName.lastIndexOf("."));
    }

    @Override
    public void load() {

    }

    @Override
    public boolean delete(String filePath) throws IOException {
        return Files.deleteIfExists(Paths.get(filePath));
    }
}
