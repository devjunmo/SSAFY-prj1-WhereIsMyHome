package com.ssafy.home.user.service;

import com.ssafy.home.user.dto.UserAuthRequest;
import com.ssafy.home.user.dto.UserDto;
import com.ssafy.home.user.dto.UserPasswordDto;
import java.util.Map;

public interface UserService {

    void signUp(UserDto userDto);

    Map<String, Object> signIn(UserDto userDto);

    void sendRegisterCode(UserAuthRequest authRequest);
    void checkValidEmail(String email);
    Map<String, Object> refresh();

    void logout();

    void verifyRegisterCode(UserAuthRequest authRequest);
    void updateUser(UserPasswordDto passwordDto);
    void deleteUser();
    void sendChangePasswordVerifyCode(UserAuthRequest authRequest);
    void sendChangePassword(UserAuthRequest authRequest);
}

