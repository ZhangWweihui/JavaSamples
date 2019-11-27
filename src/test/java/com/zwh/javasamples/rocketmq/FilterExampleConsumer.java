package com.zwh.javasamples.rocketmq;

import com.zwh.utils.Constants;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 过滤消息
 * @author ZhangWeihui
 * @date 2019/11/27 10:08
 */
public class FilterExampleConsumer {

    private DefaultMQPushConsumer consumer = null;

    @Before
    public void init(){
        consumer = new DefaultMQPushConsumer("filter_example_consumers");
        consumer.setInstanceName("filterExampleConsumer");
        consumer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);//不知为何只能用广播模式消费
    }

    /**
     * 用 tag 过滤消息
     * @throws Exception
     */
    @Test
    public void consume1() throws Exception {
        //下面两种写法都可以
        //consumer.subscribe(Constants.TOPIC_ARTIFACT_INFO, "TAG-A||TAG-B||TAG-C");
        consumer.subscribe(Constants.TOPIC_ARTIFACT_INFO, MessageSelector.byTag("TAG-A||TAG-B"));
        // Register message listener
        consumer.registerMessageListener((List<MessageExt> messages, ConsumeConcurrentlyContext context) -> {
//            for (MessageExt message : messages) {
//                // Print approximate delay time period
//                System.out.println(JSON.toJSONString(message));
//            }
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), messages);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // Launch consumer
        consumer.start();
        System.out.println("Consumer started.");
        System.in.read();
    }

    /**
     * 用 SQL92 过滤消息
     * @throws Exception
     */
    @Test
    public void consume2() throws Exception {
        //consumer.subscribe(Constants.TOPIC_ARTIFACT_INFO, MessageSelector.bySql("(a between 0 and 2) or b=0"));
        consumer.subscribe(Constants.TOPIC_ARTIFACT_INFO, MessageSelector.bySql("region in ('Beijing','Shanghai')"));
        consumer.setVipChannelEnabled(false);
        // Register message listener
        consumer.registerMessageListener((List<MessageExt> messages, ConsumeConcurrentlyContext context) -> {
//            for (MessageExt message : messages) {
//                // Print approximate delay time period
//                System.out.println(JSON.toJSONString(message));
//            }
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), messages);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // Launch consumer
        consumer.start();
        System.out.println("Consumer started.");
        System.in.read();
    }

}
