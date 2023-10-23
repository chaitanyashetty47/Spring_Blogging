package com.chaireads.blogs.services.impl;

import com.chaireads.blogs.services.fileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class fileServiceImpl implements fileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //file name
        String name = file.getOriginalFilename();

        //random name generate file
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(name.substring(name.indexOf(".")));

        //Full Path
        String filePath = path + File.separator + fileName1;

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);

        return is;
    }
}
