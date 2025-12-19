package org.a2ajava.agent.kafka;


import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.detect.ActionState;
import io.github.vishalmysore.common.A2AActionCallBack;
import org.springframework.stereotype.Service;

@Service
@Agent(groupName = "order support", groupDescription = "处理订单相关的操作")
public class OrderService {
    private A2AActionCallBack callBack;

    @Action(description = "处理新订单")
    public String processNewOrder(String orderId, String status, String amount) {
        // 在任务执行过程中发送状态更新
        if (callBack != null) {
            callBack.sendtStatus("处理中: 订单ID=" + orderId, ActionState.WORKING);
            // TODO: 这里添加订单处理逻辑
            callBack.sendtStatus("完成: 订单ID=" + orderId, ActionState.COMPLETED);
        }
        return "已处理订单: " + orderId;
    }
}
