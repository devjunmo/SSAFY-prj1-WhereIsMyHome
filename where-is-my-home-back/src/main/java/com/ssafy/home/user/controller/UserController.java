package com.ssafy.home.user.controller;

import com.ssafy.home.etc.annotation.Auth;
import com.ssafy.home.user.dto.UserAuthRequest;
import com.ssafy.home.user.dto.UserDto;
import com.ssafy.home.user.dto.UserPasswordDto;
import com.ssafy.home.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto) {
        userService.signUp(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody UserDto userDto) {
        // 로그인 성공시 "accessToken", "refreshToken"을 key로 하는 토큰 데이터가 담긴 hash map 리턴
        return new ResponseEntity<>(userService.signIn(userDto), HttpStatus.OK);
    }

    // 아이디 중복체크
    @GetMapping("/signUp/checkEmail")
    public ResponseEntity<Void> checkValidEmail(@RequestParam String email) {
        userService.checkValidEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signUp/sendMail")
    public ResponseEntity<Void> sendRegisterCode(@RequestBody UserAuthRequest authRequest) {
        userService.sendRegisterCode(authRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signUp/verify")
    public ResponseEntity<Void> verifyRegisterCode(@RequestBody UserAuthRequest authRequest) {
        userService.verifyRegisterCode(authRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh() {
        return new ResponseEntity<>(userService.refresh(), HttpStatus.OK);
    }

    @Auth
    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        userService.logout();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Auth
    @PostMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserPasswordDto passwordDto) {
        userService.updateUser(passwordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Auth
    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/password/sendmail")
    public ResponseEntity<Void> sendChangePasswordVerifyCode(@RequestBody UserAuthRequest authRequest) {
        userService.sendChangePasswordVerifyCode(authRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password/verify")
    public ResponseEntity<Void> sendChangePassword(@RequestBody UserAuthRequest authRequest) {
        userService.sendChangePassword(authRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
