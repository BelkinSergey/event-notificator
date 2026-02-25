package dev.belkin.notificator.kafka;


import dev.belkin.notificator.dto.NotificationDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<@NonNull Integer, @NonNull NotificationDto> consumerFactory() {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "notificator-group");
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        configProperties.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, NotificationDto.class );
        configProperties.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "belkin.dev.events.kafka.dto" );
        configProperties.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS, false );

        return new DefaultKafkaConsumerFactory<@NonNull Integer, @NonNull NotificationDto>(configProperties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<@NonNull Integer, @NonNull NotificationDto> kafkaListenerContainerFactory(
            ConsumerFactory<@NonNull Integer, @NonNull NotificationDto> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<@NonNull Integer, @NonNull NotificationDto>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
