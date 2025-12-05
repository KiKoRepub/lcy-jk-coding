package org.agentremote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AgentRemoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentRemoteApplication.class, args);
        log.info("Agent-Remote Application started successfully.");
    }
}