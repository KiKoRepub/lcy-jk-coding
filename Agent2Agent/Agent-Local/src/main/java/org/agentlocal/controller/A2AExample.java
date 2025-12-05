package org.agentlocal.controller;


import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.agent.a2a.A2aRemoteAgent;
import com.alibaba.cloud.ai.graph.agent.a2a.AgentCardProvider;
import com.alibaba.cloud.ai.graph.agent.a2a.AgentCardWrapper;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import io.a2a.spec.AgentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class A2AExample {
    private final AgentCardProvider agentCardProvider;

    @Autowired
    public A2AExample(@Qualifier("localAgentCardProvider") AgentCardProvider agentCardProvider) {
        this.agentCardProvider = agentCardProvider;
    }


    public void callRemoteAgent() throws GraphRunnerException {
        // 通过 AgentCardProvider 从注册中心发现 Agent
        A2aRemoteAgent remote = A2aRemoteAgent.builder()
                .name("data_analysis_agent")
                .agentCardProvider(agentCardProvider)  // 从 Nacos 自动获取 AgentCard
                .description("数据分析远程代理")
                .build();

        // 远程调用
//        Optional<OverAllState> result = remote.invoke("请根据季度数据给出同比与环比分析概要。");
//
//        result.ifPresent(state -> {
//            System.out.println("调用成功: " + state.value("output"));
//        });
        AgentCardWrapper agentCard = agentCardProvider.getAgentCard();

        System.out.println(agentCard.description());

    }
}
