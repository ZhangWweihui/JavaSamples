package com.zwh.javasamples.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2018/11/28 21:49
 */
@Slf4j
public class RocketMQSpringConsumer {

    private DefaultMQPushConsumer consumer1 = null;
    private DefaultMQPushConsumer consumer2 = null;

    @Before
    public void init(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appContext.xml");
        consumer1 = (DefaultMQPushConsumer) applicationContext.getBean("artifactConsumer1");
        consumer2 = (DefaultMQPushConsumer) applicationContext.getBean("artifactConsumer2");
        Assert.assertNotNull(consumer1);
        Assert.assertNotNull(consumer2);
    }

    @Test
    public void test(){
        try {
            consumer1.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                    for (Message msg : msgs) {
                        String msgStr = new String(msg.getBody());
                        log.info("消费者1 获取结果为：{}", msgStr);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            consumer2.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                    for (Message msg : msgs) {
                        String msgStr = new String(msg.getBody());
                        log.info("消费者2 获取结果为：{}", msgStr);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            consumer1.start();
            consumer2.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
