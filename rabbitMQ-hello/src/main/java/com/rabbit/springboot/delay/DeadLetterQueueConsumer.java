package com.rabbit.springboot.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class DeadLetterQueueConsumer {

    private final Logger log = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws IOException
    {String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), msg);
    }
}
