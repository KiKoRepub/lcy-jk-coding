package org.agentremote;

import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.spec.AgentCard;
import org.agentremote.excutor.WeatherAgentExecutorProducer;
import org.agentremote.producer.WeatherAgentCardProducer;

public class Main {

    public static void main(String[] args) {


        AgentExecutor agentExecutor = new WeatherAgentExecutorProducer().agentExecutor();
        AgentCard agentCard = new WeatherAgentCardProducer().agentCard();
        agentExecutor.execute(null, null);
    }
}
