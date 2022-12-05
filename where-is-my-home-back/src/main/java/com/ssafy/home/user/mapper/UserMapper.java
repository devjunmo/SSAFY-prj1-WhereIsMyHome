package com.ssafy.home.user.mapper;

import com.ssafy.home.user.dto.UserDto;
import com.ssafy.home.user.dto.UserEmailAuthDto;
import com.ssafy.home.user.dto.UserRefreshTokenDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void signUp(UserDto user);
    UserDto getUserByEmail(String email);
    UserDto getUserById(int id);
    void saveUserRefreshToken(UserRefreshTokenDto userRefreshTokenDto);

    void setAuthCode(UserEmailAuthDto authDto);
    UserEmailAuthDto getAuthCode(int userId, String type);
    void expireAuthCode(int id);
    void verifyUser(int userId);

    boolean checkValidEmail(String email);
    String getUserRefreshToken(int userId);
    void deleteUserRefreshToken(int userId);
    void deleteUser(int userId);
    void updatePassword(UserDto user);
}
