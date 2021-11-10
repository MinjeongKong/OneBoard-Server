package com.connect.oneboardserver.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void init(Path path);

    String store(String uploadPath, MultipartFile file) throws Exception;

    void load();

    boolean delete(String filePath) throws IOException;
}
