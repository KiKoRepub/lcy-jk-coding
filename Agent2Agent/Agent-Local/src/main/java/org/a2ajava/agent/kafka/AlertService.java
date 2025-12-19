package org.a2ajava.agent.kafka;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import org.springframework.stereotype.Service;

@Service
@Agent(groupName = "alert support", groupDescription = "处理系统告警相关的操作")
public class AlertService {
    @Action(description = "处理系统告警")
    public String processAlert(String alertId, String type, String severity) {
        // TODO: 添加告警处理逻辑
        return "已处理告警: " + alertId;
    }
}