import com.spring.ack.SpringkafkaApplication;
import com.spring.ack.producer.ArtisanProducerMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringkafkaApplication.class)
public class ProduceMockTest {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ArtisanProducerMock artisanProducerMock;


    @Test
    public void testAsynSend() throws ExecutionException, InterruptedException {
        logger.info("开始发送");

        for (int i = 0; i < 10; i++) {
            artisanProducerMock.sendMsgASync().addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    logger.info(" 发送异常{}]]", throwable);

                }

                @Override
                public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                    logger.info("回调结果 Result =  topic:[{}] , partition:[{}], offset:[{}]",
                            objectObjectSendResult.getRecordMetadata().topic(),
                            objectObjectSendResult.getRecordMetadata().partition(),
                            objectObjectSendResult.getRecordMetadata().offset());
                }
            });
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
