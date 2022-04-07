package com.rabbit.springboot.priority;

import com.rabbit.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class Producer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel()) {

            //设置队列的最大优先级 最大可以设置到 255 官网推荐 1-10 如果设置太高比较吃内存和 CPU
            Map<String, Object> params = new HashMap();
            params.put("x-max-priority", 10);
            channel.queueDeclare(QUEUE_NAME, true, false, false, params);

            for (int i = 1; i < 11; i++) {
                String message = "info" + i;
                if (i == 5) {
                    //给消息赋予一个 priority 属性
                    AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
                    channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
                } else {
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                }
                System.out.println("发送消息完成:" + message);
            }
        }
    }
}