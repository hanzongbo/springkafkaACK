package com.spring.ack.consumer;

import com.spring.ack.domain.MessageMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ArtisanCosumerMock {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String CONSUMER_GROUP_PREFIX = "MANUAL_ACK_" ;

    @KafkaListener(topics = "mytopic" ,groupId = CONSUMER_GROUP_PREFIX + "mytopic")
    public void onMessage(MessageMock messageMock, Acknowledgment acknowledgment) throws InterruptedException {
        logger.info("【接受到消息][线程:{} 消息内容：{}]", Thread.currentThread().getName(), messageMock);
        // MOCK  BUSSINESS
        TimeUnit.SECONDS.sleep(1);
        // 手动提交消费进度
        acknowledgment.acknowledge();
    }
}
