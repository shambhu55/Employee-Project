package com.example.employee.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "uploads/employee-profiles/";

    public static String saveFile(Long employeeId, MultipartFile file) throws IOException {
        String fileName = employeeId + ".jpg";
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/" + UPLOAD_DIR + fileName;
    }
}
