package com.growbiz.backend.Business.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileStorageService implements IFileStorageService {

    private final Path root = Paths.get(System.getProperty("user.dir") + "/files");

    @Override
    public String uploadFileToStorage(MultipartFile file, String email) {

        Path folderPath = Paths.get(root + "/" + email);
        File filePath = new File(folderPath + "/" + file.getOriginalFilename());
        try {
            Files.createDirectories(folderPath);
            file.transferTo(filePath);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return filePath.getPath();
    }

    @Override
    public byte[] downloadFile(String email) {
        try {
            String folderPath = root + "/" + email;
            List<File> files = Files.list(Paths.get(folderPath))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .toList();
            return Files.readAllBytes(files.get(0).toPath());
        } catch (IOException ioException) {
            System.out.println("Error");
        }
        return null;
    }
}
