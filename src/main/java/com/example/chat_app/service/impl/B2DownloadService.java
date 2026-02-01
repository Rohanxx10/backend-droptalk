package com.example.chat_app.service.impl;

import java.io.InputStream;

public interface B2DownloadService {
    InputStream download(String bucket, String fileName);
}