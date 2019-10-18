package com.stylefeng.guns.rest.modular.oss.config;

import com.aliyun.oss.OSSClient;
import com.stylefeng.guns.rest.modular.oss.config.propertise.AliyunProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {
    @Autowired
    AliyunProperties aliyunProperties;

    @Bean
    public OSSClient ossClient() {
        String endpoint = aliyunProperties.getOss().getEndpoint();
        String accessKeyId = aliyunProperties.getAccess().getKey().getId();
        String secretAccessKey = aliyunProperties.getAccess().getKey().getSecret();
        return new OSSClient(endpoint, accessKeyId, secretAccessKey);
    }
}
