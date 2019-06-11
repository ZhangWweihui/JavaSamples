package com.zwh.javasamples.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author ZhangWeihui
 * @date 2018/11/28 16:36
 */
@Slf4j
public class RocketMQProducerTest {

    public static final String TOPIC = "hshcics_policy_info";
    public static final String TAG = "sync_policy_infos";
    public static final String NAMESRV = "172.18.20.37:9876;172.18.20.47:9876";
    private static final String INSTANCE_NAME = "hshcics_policy_info_producer";
        private static final String GROUP_NAME = "policy_info_producers";

    private DefaultMQProducer producer = null;

    @Before
    public void init(){
        try {
            producer = new DefaultMQProducer(GROUP_NAME);
            producer.setNamesrvAddr(NAMESRV);
            producer.setInstanceName(INSTANCE_NAME);
            producer.setSendMsgTimeout(3000);
            producer.setRetryTimesWhenSendFailed(2);
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destroy(){
        producer.shutdown();
    }

    @Test
    public void test(){
        try {
            for(int i=0;i<8;i++){
                String key = UUID.randomUUID().toString().replace("-","");
                int randomInt = RandomUtils.nextInt(1000,9999);
                Message message = new Message(TOPIC, TAG, key, ("保险测试信息：policy_info_test_message_"+randomInt).getBytes(RemotingHelper.DEFAULT_CHARSET));
                try {
                    SendResult sendResult = producer.send(message);
                    log.info("producer send result status : {}", sendResult.getSendStatus());
                } catch (MQClientException e) {
                    e.printStackTrace();
                } catch (RemotingException e) {
                    e.printStackTrace();
                } catch (MQBrokerException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
