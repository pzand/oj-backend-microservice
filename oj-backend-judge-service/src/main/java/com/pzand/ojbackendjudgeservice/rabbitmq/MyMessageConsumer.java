package com.pzand.ojbackendjudgeservice.rabbitmq;

import com.pzand.ojbackendjudgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    // 指定程序监听的消息队列和确认机制
    // 监听哪个队列，确认模式为手动确认(回复快递员 收到消息 / 收到消息就当作已经收到货了)
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("receiveMessage message = {}", message);
        try {
            judgeService.doJudge(Long.parseLong(message));
            // 发生确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
//            channel.basicNack(deliveryTag, false, true);
        }
    }
}
