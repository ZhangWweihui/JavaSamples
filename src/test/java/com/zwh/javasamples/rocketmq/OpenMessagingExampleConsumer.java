package com.zwh.javasamples.rocketmq;

import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.consumer.Consumer;
import io.openmessaging.consumer.MessageListener;
import io.openmessaging.manager.ResourceManager;
import io.openmessaging.message.Message;
import org.junit.Test;

import java.util.Arrays;

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
                OMS.getMessagingAccessPoint("oms:rocketmq://localhost:10911/us-east");

        //Fetch a ResourceManager to create Queue resource.
        ResourceManager resourceManager = messagingAccessPoint.resourceManager();
        resourceManager.createNamespace("NS://XXXX");
        final Consumer consumer = messagingAccessPoint.createConsumer();
        consumer.start();

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.stop();
            }
        }));

        //Consume messages from a simple queue.
        String simpleQueue = "NS://HELLO_QUEUE";
        resourceManager.createQueue(simpleQueue);
        //This queue doesn't has a source queue, so only the message delivered to the queue directly can
        //be consumed by this consumer.
        consumer.bindQueue(Arrays.asList(simpleQueue), new MessageListener() {
            @Override
            public void onReceived(Message message, Context context) {
                System.out.println("Received one message: " + message);
                context.ack();
            }

        });

        consumer.unbindQueue(Arrays.asList(simpleQueue));

        consumer.stop();
    }

    @Test
    public void pullConsume() {
        //Load and start the vendor implementation from a specific OMS driver URL.
        final MessagingAccessPoint messagingAccessPoint =
                OMS.getMessagingAccessPoint("oms:rocketmq://alice@rocketmq.apache.org/us-east");

        //Start a PullConsumer to receive messages from the specific queue.
        final PullConsumer consumer = messagingAccessPoint.createPullConsumer();

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.stop();
            }
        }));

        consumer.bindQueue(Arrays.asList("NS://HELLO_QUEUE"));
        consumer.start();

        Message message = consumer.receive(1000);
        System.out.println("Received message: " + message);
        //Acknowledge the consumed message
        consumer.ack(message.getMessageReceipt());
        consumer.stop();

    }
}
