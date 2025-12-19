package org.a2ajava;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishalmysore.a2a.client.LocalA2ATaskClient;
import io.github.vishalmysore.a2a.domain.Task;
import io.github.vishalmysore.common.server.JsonRpcController;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class SpringKafkaAgent {

    private final LocalA2ATaskClient client;

    private final ObjectMapper objectMapper;

    public SpringKafkaAgent(ObjectMapper objectMapper) {
        this.client = new LocalA2ATaskClient(new JsonRpcController());
        this.objectMapper = objectMapper;
    }
//    git config --global core.sshCommand 'ssh -i ~/.ssh/id_rsa.pub'


    // 将 Kafka 消息转换为 A2A 任务并发送
    private void processMessage(String messageType, String topic, String key, String value) {
        // 构建任务描述信息
        String description = String.format("Kafka消息任务: 类型=%s, 主题=%s, 键=%s, 值=%s",
                messageType, topic, key, value);
        // 使用 A2A 客户端发送任务
        Task task = client.sendTask(description);
        // 等待任务完成（超时时间秒）
        Task result = client.getTask(task.getId(), 5);
        // TODO: 可以根据 result 获取任务输出并处理
        System.out.println(result);
    }

    @KafkaListener(topics = "orders", groupId = "a2a-group")
    public void consumeOrderMessages(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            processMessage("order-processing", record.topic(), record.key(), record.value());
            ack.acknowledge();
        } catch (Exception e) {
            // 错误处理逻辑
        }
    }

    // 可类似地为 payments 和 alerts 主题添加监听方法
}
