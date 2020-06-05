package com.zwh.javasamples.rocketmq;

import io.openmessaging.api.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 使用 OpenMessaging 消费消息
 * @author ZhangWeihui
 * @date 2019/11/28 12:04
 */
public class OpenMessagingExampleConsumer {

    @Test
    public void pushConsume() {
        //Load and start the vendor implementation from a specific OMS driver URL.
        final MessagingAccessPoint messagingAccessPoint =
                OMS.builder()
                        .region("Shenzhen")
                        .endpoint("127.0.0.1:9876")
                        .driver("rocketmq")
                        .withCredentials(new Properties())
                        .build();

        Properties properties = new Properties();
        final Consumer consumer = messagingAccessPoint.createConsumer(properties);
        consumer.start();

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.shutdown();
            }
        }));

        //Consume messages from a simple queue.
        String topic = "NS://HELLO_TOPIC";

        consumer.subscribe(topic, "*", new MessageListener(){
            @Override
            public Action consume(Message message, ConsumeContext context) {

                return Action.CommitMessage;
            }
        });

        consumer.shutdown();
    }

    @Test
    public void pullConsume() {
        //Load and start the vendor implementation from a specific OMS driver URL.
        final MessagingAccessPoint messagingAccessPoint =
                OMS.builder()
                        .endpoint("http://mq-instance-xxx-1234567890-test:8080")
                        .region("Shenzhen")
                        .driver("rocketmq")
                        .build();

        Properties properties = new Properties();
        //Start a PullConsumer to receive messages from the specific queue.
        final PullConsumer consumer = messagingAccessPoint.createPullConsumer(properties);

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.shutdown();
            }
        }));

        Set<TopicPartition> topicPartitions = consumer.topicPartitions("NS://TOPIC");
        consumer.assign(topicPartitions);
        consumer.start();

        List<Message> message = consumer.poll(1000);
        System.out.println("Received message: " + message);
        //Acknowledge the consumed message
        consumer.commitSync();
        consumer.shutdown();
    }
}
