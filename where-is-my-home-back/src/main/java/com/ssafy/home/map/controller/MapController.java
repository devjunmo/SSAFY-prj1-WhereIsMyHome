package com.ssafy.home.map.controller;

import com.ssafy.home.etc.annotation.Auth;
import com.ssafy.home.map.dto.DongCodeDto;
import com.ssafy.home.map.dto.FavoriteDto;
import com.ssafy.home.map.dto.HouseDealDto;
import com.ssafy.home.map.dto.HouseDealPage;
import com.ssafy.home.map.dto.HouseInfoDto;
import com.ssafy.home.map.service.MapService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/sido")
    public ResponseEntity<List<DongCodeDto>> getSido() {
        return new ResponseEntity<>(mapService.getSido(), HttpStatus.OK);
    }

    @GetMapping("/gugun")
    public ResponseEntity<List<DongCodeDto>> getGugun(@RequestParam String sidoName) {
        return new ResponseEntity<>(mapService.getGugun(sidoName), HttpStatus.OK);
    }

    @GetMapping("/dong")
    public ResponseEntity<List<DongCodeDto>> getDong(@RequestParam String sidoName, @RequestParam String gugunName) {
        return new ResponseEntity<>(mapService.getDong(sidoName, gugunName), HttpStatus.OK);
    }

    @GetMapping("/apt")
    public ResponseEntity<List<HouseInfoDto>> getAptList(@RequestParam String dongCode) {
        return new ResponseEntity<>(mapService.getAptList(dongCode), HttpStatus.OK);
    }

    @GetMapping("/apt/{aptCode}/deal")
    public ResponseEntity<HouseDealPage> getAptDeal(@PathVariable Long aptCode, @RequestParam Long cur) {
        return new ResponseEntity<>(mapService.getAptDeal(aptCode, cur), HttpStatus.OK);
    }

    @PostMapping("/favorite")
    @Auth
    public ResponseEntity<FavoriteDto> addFavoriteApt(@RequestBody FavoriteDto favoriteDto) {
        return new ResponseEntity<>(mapService.addFavoriteApt(favoriteDto), HttpStatus.OK);
    }

    @DeleteMapping("/favorite/{favoriteId}")
    @Auth
    public ResponseEntity<Void> removeFavoriteApt(@PathVariable Integer favoriteId) {
        mapService.removeFavoriteApt(favoriteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/favorite")
    @Auth
    public ResponseEntity<List<HouseInfoDto>> getAllFavoriteArea() {
        return new ResponseEntity<>(mapService.getAllFavoriteArea(), HttpStatus.OK);
    }
}
