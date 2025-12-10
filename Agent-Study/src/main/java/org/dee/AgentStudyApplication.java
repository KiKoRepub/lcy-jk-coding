package org.dee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("org.dee.mapper")
public class AgentStudyApplication
{

    public static void main( String[] args )
    {
        SpringApplication.run(AgentStudyApplication.class, args);
    }
}
