package org.agentremote.agent;

import jakarta.enterprise.context.ApplicationScoped;


public interface WeatherAgentInterface {
    String chat(String userMessage);
}
