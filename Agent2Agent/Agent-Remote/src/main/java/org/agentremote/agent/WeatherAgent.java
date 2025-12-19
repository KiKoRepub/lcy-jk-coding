package org.agentremote.agent;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class WeatherAgent implements WeatherAgentInterface {
    @Override
    public String chat(String userMessage) {
        log.info("WeatherAgent received message: {}", userMessage);
        return userMessage;
    }
}
