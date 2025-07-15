package com.musicify.musicapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JamendoConfig {

    @Value("${jamendo.api.url}")
    private String apiUrl;

    @Value("${jamendo.client.id}")
    private String clientId;
}
