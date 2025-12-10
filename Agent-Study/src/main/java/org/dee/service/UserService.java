package org.dee.service;

import org.dee.entity.dto.user.UserRegisterDTO;

public interface UserService {
    Long analyzeUserIdFromToken(String userToken);


    String analyzeUserNameFromToken(String userToken);

    boolean registerNewUser(UserRegisterDTO dto);
}
