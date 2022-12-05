package com.ssafy.home.map.mapper;

import com.ssafy.home.map.dto.DongCodeDto;
import com.ssafy.home.map.dto.FavoriteDto;
import com.ssafy.home.map.dto.HouseDealDto;
import com.ssafy.home.map.dto.HouseInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapMapper {
    List<DongCodeDto> getSido();
    List<DongCodeDto> getGugun(String sidoName);
    List<DongCodeDto> getDong(String sidoName, String gugunName);

    List<HouseInfoDto> getAptList(String dongCode, Integer userId);
    List<HouseDealDto> getAptDeal(Long aptCode, Long cur);

    boolean checkExistAptCode(Long aptCode);
    FavoriteDto getFavoriteById(Integer favoriteId);
    boolean checkExistFavoriteByUserIdAndAptCode(FavoriteDto favoriteDto);
    void addFavoriteApt(FavoriteDto favoriteDto);
    void removeFavoriteApt(Integer favoriteId);
    List<HouseInfoDto> getAllFavoriteArea(Integer userId);
}
