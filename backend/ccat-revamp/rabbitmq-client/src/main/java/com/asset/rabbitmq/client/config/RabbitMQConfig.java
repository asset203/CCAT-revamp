package com.asset.rabbitmq.client.config;

import com.asset.rabbitmq.client.cache.RabbitMQProperties;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author islam.said
 */
@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Bean
    DirectExchange exchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-deduplication", true);
        return new DirectExchange(rabbitMQProperties.getExchangeName(), true, false, arguments);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQProperties.getRabbitmqHost());
        connectionFactory.setUsername(rabbitMQProperties.getRabbitmqUsername());
        connectionFactory.setPassword(rabbitMQProperties.getRabbitmqPassword());
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        connectionFactory.setChannelCacheSize(rabbitMQProperties.getMaxNumberOfChannelsPerNode());
        connectionFactory.setChannelCheckoutTimeout(rabbitMQProperties.getChannelCheckoutTimeout());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

//    @Bean
//    public BatchingRabbitTemplate batchRabbitTemplate(ConnectionFactory connectionFactory) {
//        int batchSize = rabbitMQProperties.getBatchSize(); // The maximum number of messages exceeds the number of collected messages.
//        int bufferLimit = rabbitMQProperties.getBufferLimit(); // Maximum memory for each batch of messages sent
//        int timeout = rabbitMQProperties.getTimeout(); // The maximum waiting time exceeding the collected time, unit: milliseconds
//        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);
//        // Create a TaskScheduler object to implement a timer for sending overtime
//        TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
//        // Create BatchingRabbitTemplate object
//        BatchingRabbitTemplate batchTemplate = new BatchingRabbitTemplate(batchingStrategy, taskScheduler);
//        batchTemplate.setConnectionFactory(connectionFactory);
//        return batchTemplate;
//    }
  
//    @Bean
//    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer
//            , ConnectionFactory connectionFactory) {
//        // Create SimpleRabbitListenerContainerFactory object
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();        
//        configurer.configure(factory, connectionFactory);
//        // Additional attributes for bulk consumption
//        factory.setBatchListener(true);
//        // <X>
//        factory.setBatchSize(10);
//        factory.setReceiveTimeout(30 * 1000L);
//        factory.setConsumerBatchEnabled(true);
//        return factory;
//    }

}
