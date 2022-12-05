package com.ssafy.home.map.dto;

public class HouseDealDto {
    Long no, aptCode;
    int dealDay, dealMonth, dealYear;
    String area, dealAmount, floor;

    public HouseDealDto() {
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Long getAptCode() {
        return aptCode;
    }

    public void setAptCode(Long aptCode) {
        this.aptCode = aptCode;
    }

    public int getDealDay() {
        return dealDay;
    }

    public void setDealDay(int dealDay) {
        this.dealDay = dealDay;
    }

    public int getDealMonth() {
        return dealMonth;
    }

    public void setDealMonth(int dealMonth) {
        this.dealMonth = dealMonth;
    }

    public int getDealYear() {
        return dealYear;
    }

    public void setDealYear(int dealYear) {
        this.dealYear = dealYear;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
