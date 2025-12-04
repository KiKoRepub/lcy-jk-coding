package org.agentlocal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AgentLocalApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentLocalApplication.class, args);
        log.info("Agent-Local Application started successfully.");
    }
}