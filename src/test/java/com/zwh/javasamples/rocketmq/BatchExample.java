package com.zwh.javasamples.rocketmq;

import com.alibaba.fastjson.JSON;
import com.zwh.utils.Constants;
import com.zwh.utils.ListSplitter;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量发送消息
 * Messages of the same batch should have: same topic, same waitStoreMsgOK and no schedule support.
 * Besides, the total size of the messages in one batch should be no more than 1MiB.
 * @author ZhangWeihui
 * @date 2019/11/26 12:06
 */
public class BatchExample {

    @Test
    public void test1() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appContext.xml");
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("artifactProducer");
        Assert.assertNotNull(producer);
        String topic = Constants.TOPIC_ARTIFACT_INFO;
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "[BatchMessage] Hello world 0".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID002", "[BatchMessage] Hello world 1".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID003", "[BatchMessage] Hello world 2".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        try {
            SendResult sendResult = producer.send(messages);//发送一个Message对象列表
            System.out.println(JSON.toJSONString(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

    @Test
    public void test2() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appContext.xml");
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("artifactProducer");
        Assert.assertNotNull(producer);
        String topic = Constants.TOPIC_ARTIFACT_INFO;
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "[BatchMessage] Hello world 0".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID002", "[BatchMessage] Hello world 1".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID003", "[BatchMessage] Hello world 2".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            try {
                List<Message>  listItem = splitter.next();
                producer.send(listItem);
            } catch (Exception e) {
                e.printStackTrace();
                //handle the error
            }
        }
        producer.shutdown();
    }
}
