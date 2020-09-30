package com.tenquare.test;

import com.tensquare.rabbitmq.RebbitMQApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebbitMQApplication.class)
public class ProductTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMSg() {
        rabbitTemplate.convertAndSend("itcast","直接模式测试");
    }

}
