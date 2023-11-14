package com.growbiz.backend.File.service;


import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    public String uploadFileToStorage(MultipartFile file, String email);

    public byte[] downloadFile(String email);
}
