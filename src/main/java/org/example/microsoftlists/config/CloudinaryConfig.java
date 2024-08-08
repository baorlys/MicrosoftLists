package org.example.microsoftlists.config;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    Dotenv dotenv = Dotenv.load();
    private final String cloudName = dotenv.get("cloudinary.cloud.name");

    private final String apiKey = dotenv.get("cloudinary.api.key");

    private final String apiSecret = dotenv.get("cloudinary.api.secret");

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

}