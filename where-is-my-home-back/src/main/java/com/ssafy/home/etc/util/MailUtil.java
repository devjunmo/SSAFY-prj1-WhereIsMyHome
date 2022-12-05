package com.ssafy.home.etc.util;

import com.ssafy.home.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;

    public void sendAuthCodeByMail(UserDto user, String authNum) {

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(user.getEmail());
        simpleMessage.setSubject("인증번호 전송");
        simpleMessage.setText(authNum);
        mailSender.send(simpleMessage);
    }

    public void sendPasswordByMail(UserDto user, String password) {

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(user.getEmail());
        simpleMessage.setSubject("비밀번호 전송");
        simpleMessage.setText(password);
        mailSender.send(simpleMessage);
    }
}