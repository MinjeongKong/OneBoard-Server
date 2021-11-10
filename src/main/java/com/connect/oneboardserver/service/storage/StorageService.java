package com.connect.oneboardserver.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    void store(MultipartFile file) throws Exception;

    void load();

    void delete();
}
