package com.zwh.javasamples.rocketmq;

import io.openmessaging.Future;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.interceptor.Context;
import io.openmessaging.interceptor.ProducerInterceptor;
import io.openmessaging.message.Message;
import io.openmessaging.producer.Producer;
import io.openmessaging.producer.SendResult;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用 OpenMessaging 发送消息
 * @author ZhangWeihui
 * @date 2019/11/27 13:55
 */
public class OpenMessagingExampleProducer {

    private static final String OMS_URL = "oms:rocketmq://alice@rocketmq.apache.org/us-east";

    @Test
    public void produce() {
        final MessagingAccessPoint messagingAccessPoint = OMS.getMessagingAccessPoint(OMS_URL);
        final Producer producer = messagingAccessPoint.createProducer();
        ProducerInterceptor interceptor = new ProducerInterceptor() {
            @Override
            public void preSend(Message message, Context attributes) {
                System.out.println("PreSend message: " + message);
            }

            @Override
            public void postSend(Message message, Context attributes) {
                System.out.println("PostSend message: " + message);
            }
        };
        producer.addInterceptor(interceptor);
        producer.start();

        //Register a shutdown hook to close the opened endpoints.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                producer.stop();
            }
        }));

        //Send a message to the specified destination synchronously.
        Message message = producer.createMessage(
                "NS://HELLO_QUEUE1", "HELLO_BODY".getBytes(Charset.forName("UTF-8")));
        message.header().setBornHost("127.0.0.1").setDurability((short) 0);
        message.extensionHeader().get().setPartition(1);
        SendResult sendResult = producer.send(message);
        System.out.println("SendResult: " + sendResult);

        //Sends a message to the specified destination async.
        Future<SendResult> sendResultFuture = producer.sendAsync(message);
        sendResult = sendResultFuture.get(1000);
        System.out.println("SendResult: " + sendResult);

        //Sends a message to the specified destination in one way mode.
        producer.sendOneway(message);

        //Sends messages to the specified destination in batch mode.
        List<Message> messages = new ArrayList<Message>(10);
        for (int i = 0; i < 10; i++) {
            Message msg = producer.createMessage("NS://HELLO_QUEUE", ("Hello" + i).getBytes());
            messages.add(msg);
        }

        producer.send(messages);
        producer.removeInterceptor(interceptor);
        producer.stop();
    }
}
