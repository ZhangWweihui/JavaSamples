package com.zwh.javasamples.rocketmq;

import com.alibaba.fastjson.JSON;
import com.zwh.utils.Constants;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 创建带用户属性的消息，可以用 SQL92 查询
 * @author ZhangWeihui
 * @date 2019/11/26 16:31
 */
public class FilterExampleProducer {

    @Test
    public void produce() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appContext.xml");
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("artifactProducer");
        producer.setInstanceName("FilterExampleProducer");
        Assert.assertNotNull(producer);
        for(int i=0; i<3; i++) {
            Message msg = new Message(Constants.TOPIC_ARTIFACT_INFO, "TAG-A", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("a", String.valueOf(i));//添加用户属性 a，可以用a的值来查询消息
            msg.putUserProperty("region", "Beijing");
            SendResult sendResult = producer.send(msg);
            System.out.println(JSON.toJSONString(sendResult));
        }
        for(int i=0; i<3; i++) {
            Message msg = new Message(Constants.TOPIC_ARTIFACT_INFO, "TAG-B", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("b", String.valueOf(i));
            msg.putUserProperty("region", "Shanghai");
            SendResult sendResult = producer.send(msg);
            System.out.println(JSON.toJSONString(sendResult));
        }
        for(int i=0; i<3; i++) {
            Message msg = new Message(Constants.TOPIC_ARTIFACT_INFO, "TAG-C", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("c", String.valueOf(i));
            msg.putUserProperty("region", "Xian");
            SendResult sendResult = producer.send(msg);
            System.out.println(JSON.toJSONString(sendResult));
        }
        producer.shutdown();
    }

}
