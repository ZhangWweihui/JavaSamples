package com.zwh.javasamples.rocketmq;

import com.zwh.utils.Constants;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Simple message example
 * Use RocketMQ to send messages in three ways: reliable synchronous, reliable asynchronous, and one-way transmission.
 * Use RocketMQ to consume messages
 * @author ZhangWeihui
 * @date 2019/11/25 17:35
 */
public class SimpleMessageExample {

    public static final String PRODUCER_GROUP = "simple_message_example_producer";
    public static final String CONSUMER_GROUP = "simple_message_example_consumer";
    public static final String TOPIC = "TopicTest";

    //Send Messages Synchronously
    //Reliable synchronous transmission is used in extensive scenes,
    //such as important notification messages, SMS notification, SMS marketing system, etc..
    @Test
    public void syncSendMessage() {
        try {
            //Instantiate with a producer group name.
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            // Specify name server addresses.
            producer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
            producer.setSendMsgTimeout(3000);
            producer.setRetryTimesWhenSendFailed(3);
            producer.setInstanceName("SMEProducer");
            producer.setVipChannelEnabled(false);
            //Launch the instance.
            producer.start();
            for (int i = 0; i < 100; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message(TOPIC,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                //Call send message to deliver message to one of brokers.
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }
            //Shut down once the producer instance is not longer in use.
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Send Messages Asynchronously
    //Asynchronous transmission is generally used in response time sensitive business scenarios.
    @Test
    public void asyncSendMessage() throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
        producer.setSendMsgTimeout(3000);
        producer.setRetryTimesWhenSendFailed(3);
        producer.setInstanceName("SMEProducer");
        producer.setVipChannelEnabled(false);
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(TOPIC,
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        //Shut down once the producer instance is not longer in use.
        //producer.shutdown();
        System.in.read();
    }

    //Send Messages in One-way Mode
    //One-way transmission is used for cases requiring moderate reliability, such as log collection.
    @Test
    public void onewaySendMessage() throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
        producer.setSendMsgTimeout(3000);
        producer.setRetryTimesWhenSendFailed(3);
        producer.setInstanceName("SMEProducer");
        producer.setVipChannelEnabled(false);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(TOPIC,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

    //Consume Messages
    @Test
    public void consumer() throws Exception {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);

        // Specify name server addresses.
        consumer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);

        // Subscribe one more more topics to consume.
        consumer.subscribe(TOPIC, "*");
        consumer.setInstanceName("artifactConsumer2");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");
        System.in.read();
    }

}
