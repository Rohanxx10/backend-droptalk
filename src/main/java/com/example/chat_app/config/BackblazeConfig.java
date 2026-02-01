package com.example.chat_app.config;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackblazeConfig {

    @Bean
    public B2StorageClient b2StorageClient(BackblazeProperties props) {
        return B2StorageClientFactory
                .createDefaultFactory()
                .create(
                        props.getKeyId(),
                        props.getApplicationKey(),
                        "spring-boot-app"
                );
    }
}
