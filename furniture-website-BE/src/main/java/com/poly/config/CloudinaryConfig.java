package com.poly.config;

import com.cloudinary.Cloudinary;
import com.poly.type.properties.CloudinaryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Autowired
    CloudinaryProperties cloudinaryProperties;

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudinaryProperties.getCloudName());
        config.put("api_key", cloudinaryProperties.getCloudApiKey());
        config.put("api_secret", cloudinaryProperties.getCloudSecretKey());
        return new Cloudinary(config);
    }
}

