package com.poly.type.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("cloudinary")
public class CloudinaryProperties {
    private String cloudName;
    private String cloudApiKey;
    private String cloudSecretKey;
}
