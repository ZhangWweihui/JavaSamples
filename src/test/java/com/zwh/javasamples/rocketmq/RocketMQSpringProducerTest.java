package com.zwh.javasamples.rocketmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author ZhangWeihui
 * @date 2018/11/28 21:35
 */
@Slf4j
public class RocketMQSpringProducerTest {

    public static final String TOPIC = "artifact_info";
    public static final String TAG = "test";

    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appContext.xml");
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("artifactProducer");
        Assert.assertNotNull(producer);
        try {
            for(int i=0;i<3;i++){
                String key = UUID.randomUUID().toString().replace("-","");
                int randomInt = RandomUtils.nextInt(1000,9999);
                Message message = new Message(TOPIC, TAG, key, ("artifact_info_test_message_"+randomInt).getBytes(RemotingHelper.DEFAULT_CHARSET));
                try {
                    SendResult sendResult = producer.send(message);
                    log.info("producer send result : {}", JSON.toJSONString(sendResult));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
