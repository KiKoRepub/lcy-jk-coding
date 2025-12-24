package org.lcy.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication(scanBasePackages = "org.lcy.flowable")
public class DynamicFlowableApplication {
    public static void main(String[] args) {
        // 指定运行的环境 profile 为 flowable
        SpringApplication application = new SpringApplication(DynamicFlowableApplication.class);

        application.setDefaultProperties(
                Collections.singletonMap("spring.profiles.active", "flowable")
        );
        application.run(args);
    }
}
