package com.example.chat_app.service.impl;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2ListFileVersionsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class BackblazeDeleteService {

    private final B2StorageClient b2Client;

    @Value("${backblaze.bucket-id}")
    private String bucketId;

    public BackblazeDeleteService(B2StorageClient b2Client) {
        this.b2Client = b2Client;
    }

    public void deleteFileByName(String fileName) {
        try {
            B2ListFileVersionsRequest request =
                    B2ListFileVersionsRequest
                            .builder(bucketId)
                            .setStartFileName(fileName)
                            .build();

            Iterator<B2FileVersion> versions =
                    b2Client.fileVersions(request).iterator();

            while (versions.hasNext()) {
                B2FileVersion version = versions.next();

                // stop once filename changes
                if (!fileName.equals(version.getFileName())) {
                    break;
                }

                // ✅ CORRECT delete call
                b2Client.deleteFileVersion(
                        version.getFileName(),
                        version.getFileId()
                );
            }

        } catch (B2Exception e) {
            System.err.println("Backblaze delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
