package com.zwh.javasamples.rocketmq;

import com.alibaba.fastjson.JSON;
import com.zwh.utils.Constants;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import java.util.List;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

/**
 * 延迟消息
 * @Description
 * @Author 张炜辉
 * @Date 2019/11/25
 */
public class ScheduleExample {

    @Test
    public void consume() throws Exception {
        // Instantiate message consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ScheduleExampleConsumer");
        consumer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
        // Subscribe topics
        consumer.subscribe(Constants.TOPIC_ARTIFACT_INFO, "*");
        consumer.setInstanceName("SEConsumer");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);//不知为何只能用广播模式消费
        // Register message listener
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt message : messages) {
                    // Print approximate delay time period
                    System.out.println("Receive message[msgId=" + message.getMsgId() + "] "
                            + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // Launch consumer
        consumer.start();
        System.out.println("Consumer started.");
        System.in.read();
    }

    @Test
    public void send() throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
        producer.setSendMsgTimeout(10000);
        producer.setRetryTimesWhenSendFailed(3);
        producer.setInstanceName("SEProducer");
        producer.setVipChannelEnabled(false);
        // Launch producer
        producer.start();
        int totalMessagesToSend = 1;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message(Constants.TOPIC_ARTIFACT_INFO, ("Hello scheduled message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            SendResult sendResult = producer.send(message);
            System.out.println(JSON.toJSONString(sendResult));
        }
        // Shutdown producer after use.
        producer.shutdown();
    }
}
