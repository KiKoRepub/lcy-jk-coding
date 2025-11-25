package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apiguardian.api.API;
import org.example.entity.User;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "用户管理")
@RequestMapping("/user")
public class UserController {



    @GetMapping("/list")
    @Operation(summary = "获取用户列表", description = "返回预定义的用户列表")
    public List<User> getUserList() {

        List<User> result = List.of(
                new User(1L, "donk", "password123"),
                new User(2L, "Twistzz", "password123"),
                new User(3L, "Niko", "password123")

        );

        result.forEach(System.out::println);

        return result;
    }
}
