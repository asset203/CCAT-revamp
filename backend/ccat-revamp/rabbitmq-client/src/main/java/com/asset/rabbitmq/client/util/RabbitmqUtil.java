package com.asset.rabbitmq.client.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

/**
 *
 * @author islam.said
 */
@Component
public class RabbitmqUtil {

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    DirectExchange directExchange;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Autowired
//    BatchingRabbitTemplate batchingRabbitTemplate;

    public void createNewQueue(String queueName, String routingKey) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-deduplication", true);
        Queue queue = new Queue(queueName, true, false, false, arguments);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
    }

    public DirectMessageListenerContainer connectToExistingQueue(String queueName, Object delegateInstance, String defaultListenerMethod,
            int numberOfConsumersPerQueue) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-deduplication", true);
        Queue queue = new Queue(queueName, true, false, false, arguments);

        DirectMessageListenerContainer container = new DirectMessageListenerContainer();
        container.setAcknowledgeMode(AcknowledgeMode.NONE);
        container.setConsumersPerQueue(numberOfConsumersPerQueue);
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setMessageListener(new MessageListenerAdapter(delegateInstance, defaultListenerMethod));
        container.start();
        return container;
    }

    public void publishMsgToQueue(String exchangeName, String routingKey, Object msg) { //dirsct key config , rouit key = queue name
        byte[] rabbitMessage = SerializationUtils.serialize(msg);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, rabbitMessage);
    }

//    public void publishBatchToQueue(String exchangeName, String routingKey, List<Object> massages) {
//        for (Object message : massages) {
//            byte[] rabbitMessage = SerializationUtils.serialize(message);
//            batchingRabbitTemplate.convertAndSend(exchangeName, routingKey, rabbitMessage);
//        }
//    }

    public void deleteExistingQueue(String queueName) {
        rabbitAdmin.deleteQueue(queueName);
    }

}
