package com.growbiz.backend.File.service;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileStorageService implements IFileStorageService {

    @Autowired
    private IBusinessService businessService;

    private final Path root = Paths.get(System.getProperty("user.dir") + "/files");

    @Override
    public String uploadFileToStorage(MultipartFile file, String email) {

        Path folderPath = Paths.get(root + "/" + email);
        File filePath = new File(folderPath + "/" + file.getOriginalFilename());
        try {
            FileSystemUtils.deleteRecursively(new File(folderPath.toString()));
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
            Business business = businessService.findByEmail(email);
            String folderPath = business.getFileURL();
            List<File> files = Files.list(Paths.get(folderPath.substring(0, folderPath.lastIndexOf("/"))))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .toList();
            return Files.readAllBytes(files.get(0).toPath());
        } catch (IOException ioException) {
            System.out.println("Error in FileStorageService.downloadFile " + ioException);
        }
        return null;
    }
}
