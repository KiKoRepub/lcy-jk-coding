package org.dee.service.impl;

import io.jsonwebtoken.Claims;
import org.dee.service.UserService;
import org.dee.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public String analyzeUserIdFromToken(String userToken) {





        Claims parse = JwtUtil.parse(userToken);
        String userName = parse.getSubject();




        return userToken;
    }
}
