package com.Kshitiz.blog.services.impl;

import com.Kshitiz.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String originalName = file.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String newFileName = randomID + extension;

        String filePath = path + File.separator + newFileName;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }


    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath= path+File.separator+fileName;
        InputStream is= new FileInputStream(fullPath);
        //db logic to return input stream
        return is;
    }
}
