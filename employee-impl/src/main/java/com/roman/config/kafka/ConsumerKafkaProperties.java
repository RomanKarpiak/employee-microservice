package com.roman.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kafka")
public class ConsumerKafkaProperties {

    public static final String CONSUMER_GROUP_ID = "mycompany";
    private String bootstrapAddress;

}
