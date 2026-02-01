package com.example.chat_app.service.impl;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentHandlers.B2ContentFileWriter;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.example.chat_app.config.BackblazeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class BackblazeDownloadService {

    private final B2StorageClient client;
    private final BackblazeProperties props;

    public File downloadToDisk(String fileName) throws B2Exception {

        File file = new File("downloads/" + fileName);
        file.getParentFile().mkdirs();

        client.downloadByName(
                props.getBucketName(),
                fileName,
                B2ContentFileWriter.builder(file).build()
        );

        return file;
    }
}
