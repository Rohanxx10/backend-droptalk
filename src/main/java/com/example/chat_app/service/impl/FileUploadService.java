package com.example.chat_app.service.impl;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileUploadService {

    private final B2StorageClient client;

    @Value("${backblaze.bucket-name}")
    private String bucketName;

    public FileUploadService(B2StorageClient client) {
        this.client = client;
    }

    public String upload(MultipartFile file) throws Exception {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        B2Bucket bucket = client.getBucketOrNullByName(bucketName);

        B2UploadFileRequest request =
                B2UploadFileRequest.builder(
                        bucket.getBucketId(),
                        fileName,
                        file.getContentType(),
                        B2ByteArrayContentSource.build(file.getBytes())
                ).build();

        client.uploadSmallFile(request);

        return fileName; // ✅ RETURN FILE NAME
    }


}
