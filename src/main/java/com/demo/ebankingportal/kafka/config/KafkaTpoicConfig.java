package com.demo.ebankingportal.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTpoicConfig {
        
    // @Value(value = "${kafka.bootstrapAddress}")
    // private String bootstrapAddress;

    // @Bean
    // public KafkaAdmin kafkaAdmin() {
    //     Map<String, Object> configs = new HashMap<>();
    //     configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    //     return new KafkaAdmin(configs);
    // }
    
    // @Bean
    // public NewTopic transactionTopic() {
    //     return TopicBuilder.name("eBanking").build();
    // }

    // @Bean
    // public NewTopic transactionTopicJson() {
    //     return TopicBuilder.name("eBanking_json").build();
    // }
}
