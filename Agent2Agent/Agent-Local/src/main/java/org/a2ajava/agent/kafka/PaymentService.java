package org.a2ajava.agent.kafka;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import org.springframework.stereotype.Service;

@Service
@Agent(groupName = "payment support", groupDescription = "处理支付相关的操作")
public class PaymentService {
    @Action(description = "处理支付")
    public String processPayment(String paymentId, String status, String amount) {
        // TODO: 添加支付处理逻辑
        return "已完成支付: " + paymentId;
    }
}

