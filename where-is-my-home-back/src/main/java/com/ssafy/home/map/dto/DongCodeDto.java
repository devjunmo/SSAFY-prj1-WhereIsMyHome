package com.ssafy.home.map.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DongCodeDto {
    String dongCode, sidoName, gugunName, dongName;

    public DongCodeDto() {
    }

    public DongCodeDto(String dongCode, String sidoName, String gugunName, String dongName) {
        this.dongCode = dongCode;
        this.sidoName = sidoName;
        this.gugunName = gugunName;
        this.dongName = dongName;
    }

    public String getDongCode() {
        return dongCode;
    }

    public void setDongcode(String dongCode) {
        this.dongCode = dongCode;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getGugunName() {
        return gugunName;
    }

    public void setGugunName(String gugunName) {
        this.gugunName = gugunName;
    }

    public String getDongName() {
        return dongName;
    }

    public void setDongName(String dongName) {
        this.dongName = dongName;
    }
}
