package com.ssafy.home.user.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.home.etc.exception.BaseException;
import com.ssafy.home.etc.util.JwtUtil;
import com.ssafy.home.etc.util.MailUtil;
import com.ssafy.home.user.dto.UserAuthRequest;
import com.ssafy.home.user.dto.UserDto;
import com.ssafy.home.user.dto.UserEmailAuthDto;
import com.ssafy.home.user.dto.UserPasswordDto;
import com.ssafy.home.user.dto.UserRefreshTokenDto;
import com.ssafy.home.user.mapper.UserMapper;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final MailUtil mailUtil;

    @Override
    public void signUp(UserDto user) {
        // 1. id 중복 체크
        UserDto m = userMapper.getUserByEmail(user.getEmail());
        if (m != null) {
            throw new BaseException("아이디가 중복되었습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. 암호화 수행
        String encodePassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(encodePassword);

        // 3. 암호화된 member를 회원등록
        userMapper.signUp(user);
    }

    @Override
    public Map<String, Object> signIn(UserDto userDto) {
        // 회원아이디로 멤버 가져오기
        UserDto dbUser = userMapper.getUserByEmail(userDto.getEmail());
        // 아이디가 틀렸을 때
        if (dbUser == null) {
            throw new BaseException("유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        // parameter1 : rawPassword, parameter2 : encodePassword
        if (!BCrypt.checkpw(userDto.getPassword(), dbUser.getPassword())) {
            throw new BaseException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!dbUser.isVerified()) {
            throw new BaseException("인증을 먼저 진행하여야 합니다.", HttpStatus.UNAUTHORIZED);
        }
        // 토큰 발급
        String accessToken = jwtUtil.getAccessToken(dbUser);
        String refreshToken = jwtUtil.getRefreshToken(dbUser);

        // 리프레쉬 토큰을 db에 저장
        if (userMapper.getUserRefreshToken(dbUser.getId()) != null) {
            userMapper.deleteUserRefreshToken(dbUser.getId());
        }
        userMapper.saveUserRefreshToken(new UserRefreshTokenDto(dbUser.getId(), refreshToken));

        // Access token + refresh token을 리턴
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public void sendRegisterCode(UserAuthRequest authRequest) {
        UserDto loginUser = userMapper.getUserByEmail(authRequest.getEmail());
        if (loginUser == null) {
            throw new BaseException("유저정보가 존재하지 않습니다. 다시 로그인해주세요", HttpStatus.UNAUTHORIZED);
        }
        if (loginUser.isVerified()) {
            throw new BaseException("이미 인증 된 유저입니다. 인증이 필요하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        sendMail(loginUser, "REGISTER");
    }

    @Override
    public void sendChangePasswordVerifyCode(UserAuthRequest authRequest) {
        UserDto loginUser = userMapper.getUserByEmail(authRequest.getEmail());
        if (loginUser == null) {
            throw new BaseException("유저정보가 존재하지 않습니다. 다시 로그인해주세요", HttpStatus.BAD_REQUEST);
        }
        sendMail(loginUser, "PASSWORD");
    }

    @Override
    public void sendChangePassword(UserAuthRequest authRequest) {
        UserDto user = userMapper.getUserByEmail(authRequest.getEmail());
        if (user == null) {
            throw new BaseException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        UserEmailAuthDto auth = userMapper.getAuthCode(user.getId(), "PASSWORD");
        if (auth == null) {
            throw new BaseException("인증 요청 기록이 없습니다.", HttpStatus.NOT_FOUND);
        }

        if (!authRequest.getAuthCode().equals(auth.getVerifyKey())) {
            throw new BaseException("인증번호가 틀립니다. 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
        userMapper.expireAuthCode(auth.getId());

        String newPassword = makeNewPassword();
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userMapper.updatePassword(user);
        mailUtil.sendPasswordByMail(user, newPassword);
    }

    @Override
    public void verifyRegisterCode(UserAuthRequest authRequest) {
        UserDto user = userMapper.getUserByEmail(authRequest.getEmail());
        if (user == null) {
            throw new BaseException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        UserEmailAuthDto auth = userMapper.getAuthCode(user.getId(), "REGISTER");
        if (auth == null) {
            throw new BaseException("인증 요청 기록이 없습니다.", HttpStatus.NOT_FOUND);
        }

        if (!authRequest.getAuthCode().equals(auth.getVerifyKey())) {
            throw new BaseException("인증번호가 틀립니다. 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }

        userMapper.expireAuthCode(auth.getId());
        userMapper.verifyUser(auth.getUserId());
    }

    @Override
    public void updateUser(UserPasswordDto passwordDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);
        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));

        if (passwordDto.getNewPassword() == null || passwordDto.getNewPassword().equals("")) {
            throw new BaseException("새로운 비밀번호를 입력하세요.", HttpStatus.BAD_REQUEST);
        }

        UserDto user = userMapper.getUserById(userId);
        if (!BCrypt.checkpw(passwordDto.getPassword(), user.getPassword())) {
            throw new BaseException("기존 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(BCrypt.hashpw(passwordDto.getNewPassword(), BCrypt.gensalt()));
        userMapper.updatePassword(user);
    }

    @Override
    public void deleteUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);
        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));

        userMapper.deleteUser(userId);
    }

    @Override
    public void checkValidEmail(String email) {
        if (email == null || email.equals("") || email.equals("@")) {
            throw new BaseException("잘못된 입력입니다.", HttpStatus.BAD_REQUEST);
        }
        if (userMapper.checkValidEmail(email)) {
            throw new BaseException("존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Map<String, Object> refresh() {
        // refresh token 받아오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getHeader("Authorization");

        // refresh token 인증
        jwtUtil.isValidForm(refreshToken);
        refreshToken = refreshToken.substring(7);
        jwtUtil.isValidToken(refreshToken, "refreshToken");

        // refresh token 에서 유저 aud값 가져오기
        DecodedJWT payload = jwtUtil.getDecodedJWT(refreshToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));

        // DB에 저장된 refresh token과 일치하는지 비교
        if (!userMapper.getUserRefreshToken(userId).equals(refreshToken)) {
            throw new BaseException("재로그인 하세요.", HttpStatus.UNAUTHORIZED);
        }

        // 일치하면 토큰 재생성
        UserDto user = userMapper.getUserById(userId);
        String accessToken = jwtUtil.getAccessToken(user);
        HashMap<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        return tokens;
    }

    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization").substring(7);

        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));

        userMapper.deleteUserRefreshToken(userId);
    }

    private String makeRandomNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String makeNewPassword() {
        final char[] charSet = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z',
            '!', '@', '#', '$', '%', '^', '&'};

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int len = charSet.length;
        for (int i = 0; i < 10; i++) {
            sb.append(charSet[random.nextInt(len)]);
        }

        return sb.toString();
    }

    private void sendMail(UserDto user, String type) {
        UserEmailAuthDto beforeAuth = userMapper.getAuthCode(user.getId(), type);
        if (beforeAuth != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.getTime() - beforeAuth.getCreatedAt().getTime() < 5 * 60 * 1000) {
                throw new BaseException("메일전송은 5분에 한번 가능합니다.", HttpStatus.BAD_REQUEST);
            }
            userMapper.expireAuthCode(beforeAuth.getId());
        }
        UserEmailAuthDto authDto = new UserEmailAuthDto();
        authDto.setUserId(user.getId());
        authDto.setVerifyKey(makeRandomNumber());
        authDto.setType(type);
        userMapper.setAuthCode(authDto);

        // 메일 전송
        mailUtil.sendAuthCodeByMail(user, authDto.getVerifyKey());
    }
}
