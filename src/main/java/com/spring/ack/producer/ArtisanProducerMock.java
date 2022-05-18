package com.spring.ack.producer;


import com.spring.ack.domain.MessageMock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Component
public class ArtisanProducerMock {

    @Resource
    private KafkaTemplate<Object,Object> kafkaTemplate ;


    /**
     * 同步发送
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public SendResult sendMsgSync() throws ExecutionException, InterruptedException {
        // 模拟发送的消息
        Integer id = new Random().nextInt(100);
        MessageMock messageMock = new MessageMock(id,"artisanTestMessage-" + id);
        // 同步等待
        return  kafkaTemplate.send("mytopic", messageMock).get();
    }



    public ListenableFuture<SendResult<Object, Object>> sendMsgASync() throws ExecutionException, InterruptedException {
        // 模拟发送的消息
        Integer id = new Random().nextInt(100);
        MessageMock messageMock = new MessageMock(id,"messageSendByAsync-" + id);
        // 异步发送消息
        ListenableFuture<SendResult<Object, Object>> result = kafkaTemplate.send("mytopic", messageMock);
        return result ;

    }


}
