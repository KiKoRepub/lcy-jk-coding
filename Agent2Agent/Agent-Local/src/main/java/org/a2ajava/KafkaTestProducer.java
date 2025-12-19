package org.a2ajava;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 发送订单消息到 "orders" 主题
    public void sendOrderMessage(String key, String value) {
        kafkaTemplate.send("orders", key, value);
        System.out.println("✅ Sent order message - Key: " + key + ", Value: " + value);
    }

    // 发送支付消息到 "payments" 主题
    public void sendPaymentMessage(String key, String value) {
        kafkaTemplate.send("payments", key, value);
        System.out.println("✅ Sent payment message - Key: " + key + ", Value: " + value);
    }

    // 发送告警消息到 "alerts" 主题
    public void sendAlertMessage(String key, String value) {
        kafkaTemplate.send("alerts", key, value);
        System.out.println("✅ Sent alert message - Key: " + key + ", Value: " + value);
    }
}