package com.example.chat_app.controller;

import com.backblaze.b2.client.exceptions.B2Exception;
import com.example.chat_app.service.impl.BackblazeDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileDownloadController {

    private final BackblazeDownloadService downloadService;

    @GetMapping("/download")
    public void download(
            @RequestParam String fileName,
            HttpServletResponse response
    ) throws Exception {

        File file = downloadService.downloadToDisk(fileName);

        String contentType = Files.probeContentType(file.toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setHeader(
                "Content-Disposition",
                "inline; filename=\"" + file.getName() + "\""
        );
        response.setContentLengthLong(file.length());

        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            in.transferTo(out);
            out.flush();
        }
    }
}

