package com.bw.fit.bounty.common.conf;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/******
 * 测试rabbitmq消费数据
 */
@Component
@EnableScheduling
public class ClmNeedPaperMqReciever
{
    @RabbitHandler
    @RabbitListener(queues = "queues1", containerFactory = "rabbitListenerContainerFactory")
    public void testRev(@Payload String orderXML){

    }
}
