package com.rabbit.three;

import com.rabbit.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Task02 {
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel()) {
            boolean durable = true;//需要将队列QUEUE持久化
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入信息");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                //MessageProperties.PERSISTENT_TEXT_PLAIN 设置生产者发送消息为持久化消息，要求保存到磁盘上，不加这个就是保存到内存中
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                System.out.println("生产者发出消息" + message);
            }
        }
    }
}
