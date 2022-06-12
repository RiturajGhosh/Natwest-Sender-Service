package com.natwest.transactionsender;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * This class is to bootstrap the transaction service
 *
 * @author Rituraj
 */
@SpringBootApplication
public class Application {

    @Value(value = "${topic.transaction.name}")
    private String transactionTopic;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder
                .name(transactionTopic)
                .partitions(2)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
