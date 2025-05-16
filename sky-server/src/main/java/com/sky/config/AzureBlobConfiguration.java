package com.sky.config;

import com.sky.properties.AzureBlobProperties;
import com.sky.utils.AzureBlobUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AzureBlobConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AzureBlobUtil azureBlobUtil(AzureBlobProperties properties) {
        log.info("Begin to create AzureBlobUtil: {}", properties);
        return new AzureBlobUtil(properties.getConnectionString(), properties.getContainerName());
    }
}
