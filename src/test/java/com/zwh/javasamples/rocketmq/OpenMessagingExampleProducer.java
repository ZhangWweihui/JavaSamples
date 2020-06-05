package com.zwh.javasamples.rocketmq;


import io.openmessaging.api.*;
import org.junit.Test;

import java.util.Properties;

/**
 * 使用 OpenMessaging 发送消息
 * @author ZhangWeihui
 * @date 2019/11/27 13:55
 */
public class OpenMessagingExampleProducer {

    private static final String OMS_URL = "oms:rocketmq://alice@rocketmq.apache.org/us-east";

    @Test
    public void produce() {
        final MessagingAccessPoint messagingAccessPoint =
                OMS.builder()
                        .region("shanghai,shenzhen")
                        .endpoint("127.0.0.1:9876")
                        .driver("rocketmq")
                        .withCredentials(new Properties())
                        .build();

        final Producer producer = messagingAccessPoint.createProducer(new Properties());
        producer.start();

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                producer.shutdown();
            }
        }));

        Message message = new Message("NS://Topic", "TagA", "Hello MQ".getBytes());

        SendResult sendResult = producer.send(message);
        System.out.println("SendResult: " + sendResult);

        //Sends a message to the specified destination async.
        producer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("SendResult: " + sendResult);
            }

            @Override
            public void onException(OnExceptionContext context) {

            }
        });

        //Sends a message to the specified destination in one way mode.
        producer.sendOneway(message);

        producer.shutdown();
    }
}
