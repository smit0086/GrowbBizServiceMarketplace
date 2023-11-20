package com.growbiz.backend.Business.helper;

import com.growbiz.backend.RequestResponse.Business.BusinessRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class BusinessServiceHelper {
    private final Path root = Paths.get(System.getProperty("user.dir") + "/files");

    public String uploadAndGetFileURL(BusinessRequest businessRequest) {
        return uploadFileToStorage(businessRequest.getFile(), businessRequest.getEmail());
    }

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

}
