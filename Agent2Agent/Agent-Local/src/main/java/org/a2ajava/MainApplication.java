package org.a2ajava;

import io.github.vishalmysore.tools4ai.EnableAgent;
import org.a2ajava.agent.kafka.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableAgent
//@EnableAgentSecurity
@SpringBootApplication
public class MainApplication {
    @Autowired
    OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(KafkaTestProducer producer) {
        System.out.println(orderService);
        return args -> {
            // 向 Kafka 发布示例消息
            producer.sendOrderMessage("ORD-1", "{\"id\":\"ORD-1\",\"status\":\"created\",\"amount\":150.00}");
            producer.sendPaymentMessage("PAY-1", "{\"id\":\"PAY-1\",\"status\":\"authorized\",\"amount\":150.00}");
            producer.sendAlertMessage("ALT-1", "{\"type\":\"CPU_USAGE\",\"severity\":\"HIGH\",\"value\":95.2}");
        };
    }


}
