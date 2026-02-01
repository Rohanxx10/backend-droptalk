package com.example.chat_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "backblaze")
@Getter
@Setter
public class BackblazeProperties {
    private String keyId;
    private String applicationKey;
    private String bucketName;
}
