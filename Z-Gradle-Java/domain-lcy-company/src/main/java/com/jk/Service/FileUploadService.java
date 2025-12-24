package com.jk.Service;


import com.jk.enums.FilePathEnum;
import com.jk.starter.minio.service.MinIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
@Service
public class FileUploadService {

    @Autowired
    private MinIoService minIoService;
    public String uploadFile(String fileName, String fileType, InputStream inputStream, String bucketName) {

//        final String objectName = FilePathEnum.getFilePath(fileType) + fileName;

        minIoService.putObject(bucketName, fileName, inputStream);
        return fileName;
    }

    public void removeObject(String objectName, String bucketName) {
        minIoService.removeObject(bucketName, objectName);
    }
}
