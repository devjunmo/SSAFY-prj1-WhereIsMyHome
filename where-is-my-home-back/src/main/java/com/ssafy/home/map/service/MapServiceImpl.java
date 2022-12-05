package com.ssafy.home.map.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.home.etc.exception.BaseException;
import com.ssafy.home.etc.util.JwtUtil;
import com.ssafy.home.map.dto.DongCodeDto;
import com.ssafy.home.map.dto.FavoriteDto;
import com.ssafy.home.map.dto.HouseDealDto;
import com.ssafy.home.map.dto.HouseDealPage;
import com.ssafy.home.map.dto.HouseInfoDto;
import com.ssafy.home.map.mapper.MapMapper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;
    private final JwtUtil jwtUtil;

    @Override
    public List<DongCodeDto> getSido() {
        return mapMapper.getSido();
    }

    @Override
    public List<DongCodeDto> getGugun(String sidoName) {
        return mapMapper.getGugun(sidoName);
    }

    @Override
    public List<DongCodeDto> getDong(String sidoName, String gugunName) {
        return mapMapper.getDong(sidoName, gugunName);
    }

    @Override
    public List<HouseInfoDto> getAptList(String dongCode) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        Integer userId = null;
        if (accessToken != null) {
            accessToken = accessToken.substring(7);
            DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
            userId = Integer.parseInt(payload.getAudience().get(0));
        }
        return mapMapper.getAptList(dongCode, userId);
    }

    @Override
    public HouseDealPage getAptDeal(Long aptCode, Long cur) {
        List<HouseDealDto> deals =  mapMapper.getAptDeal(aptCode, cur);
        HouseDealPage response = new HouseDealPage();

        if (deals.size() < 11) {
            response.setHasNextData(false);
        } else {
            response.setCur(deals.get(10).getNo());
            deals.remove(10);
        }
        response.setDeals(deals);
        return response;
    }

    @Override
    public FavoriteDto addFavoriteApt(FavoriteDto favoriteDto) {
        if (!mapMapper.checkExistAptCode(favoriteDto.getAptCode())) {
            throw new BaseException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);
        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));
        favoriteDto.setUserId(userId);

        if (mapMapper.checkExistFavoriteByUserIdAndAptCode(favoriteDto)) {
            throw new BaseException("c", HttpStatus.BAD_REQUEST);
        }
        mapMapper.addFavoriteApt(favoriteDto);
        return favoriteDto;
    }

    @Override
    public void removeFavoriteApt(Integer favoriteId) {
        FavoriteDto favorite = mapMapper.getFavoriteById(favoriteId);
        if (favorite == null) {
            throw new BaseException("데이터가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);
        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));

        if (favorite.getUserId() != userId) {
            throw new BaseException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        mapMapper.removeFavoriteApt(favoriteId);
    }

    @Override
    public List<HouseInfoDto> getAllFavoriteArea() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);
        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        int userId = Integer.parseInt(payload.getAudience().get(0));

        return mapMapper.getAllFavoriteArea(userId);
    }
}
