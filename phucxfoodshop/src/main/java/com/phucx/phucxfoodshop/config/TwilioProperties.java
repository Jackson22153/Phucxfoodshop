package com.phucx.phucxfoodshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioProperties {
    private String clientId;
    private String clientSecret;
    private String serviceSid;
}
