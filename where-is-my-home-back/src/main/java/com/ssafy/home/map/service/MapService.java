package com.ssafy.home.map.service;

import com.ssafy.home.map.dto.DongCodeDto;
import com.ssafy.home.map.dto.FavoriteDto;
import com.ssafy.home.map.dto.HouseDealDto;
import com.ssafy.home.map.dto.HouseDealPage;
import com.ssafy.home.map.dto.HouseInfoDto;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface MapService {
    List<DongCodeDto> getSido();
    List<DongCodeDto> getGugun(String sidoName);
    List<DongCodeDto> getDong(String sidoName, String gugunName);
    List<HouseInfoDto> getAptList(String dongCode);
    HouseDealPage getAptDeal(Long aptCode, Long cur);
    FavoriteDto addFavoriteApt(FavoriteDto favoriteDto);
    void removeFavoriteApt(Integer favoriteId);
    List<HouseInfoDto> getAllFavoriteArea();
}
