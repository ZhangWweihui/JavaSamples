package com.zwh.javasamples.rocketmq;

import com.zwh.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2018/11/28 17:59
 */
@Slf4j
public class RocketMQConsumerTest {

    private static final String CONSUMER_GROUP = "policy_info_consumers_201811291607";
    private DefaultMQPushConsumer consumer = null;

    @Before
    public void init(){
        try {
            consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(Constants.ROCKETMQ_NAMESRV);
            consumer.subscribe(RocketMQProducerTest.TOPIC, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //consumer.setMessageModel(MessageModel.BROADCASTING);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        try {
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    try {
                        for(MessageExt messageExt : msgs){
                            String msg = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                            log.info("receive a message : {}", msg);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.in.read();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destroy(){
        consumer.shutdown();
    }
}
