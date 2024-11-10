package com.pzand.ojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于创建测试程序用例的交换机和队列（只需要在程序启动前执行一次）
 */
@Slf4j
public class InitRabbitMq {
    public static void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();

            // 操作消息队列的客户端，对比JDBC、redis_cli、docker_client
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "code_exchange";
            // 创建一个交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建一个队列
            String queueName = "code_queue";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routingKey");

            log.info("消息队列启动成功");
        } catch (Exception e) {
            log.error("消息队列启动失败");
        }
    }
}
