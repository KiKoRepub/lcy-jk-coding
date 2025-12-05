package org.agentlocal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class AgentLocalApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentLocalApplication.class, args);
        log.info("Agent-Local Application started successfully.");
    }
//    @Bean
//    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener(Environment environment) {
//        return event -> {
//            String port = environment.getProperty("server.port", "8080");
//            String contextPath = environment.getProperty("server.servlet.context-path", "");
//            String accessUrl = "http://localhost:" + port + contextPath + "/chatui/index.html";
//            System.out.println("\n🎉========================================🎉");
//            System.out.println("✅ Application is ready!");
//            System.out.println("🚀 Chat with you aygent: " + accessUrl);
//            System.out.println("🎉========================================🎉\n");
//        };
//    }
}