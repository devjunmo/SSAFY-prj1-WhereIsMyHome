package com.ssafy.home.map.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HouseInfoDto {
    Long aptCode;
    Integer buildYear, id;
    String apartmentName, roadName, lat, lng;
    Integer roadNameBonbun, roadNameBubun;

    public HouseInfoDto() {
    }

    public Long getAptCode() {
        return aptCode;
    }

    public void setAptCode(Long aptCode) {
        this.aptCode = aptCode;
    }

    public Integer getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Integer buildYear) {
        this.buildYear = buildYear;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Integer getRoadNameBonbun() {
        return roadNameBonbun;
    }

    public void setRoadNameBonbun(String roadNameBonbun) {
        if (roadNameBonbun != null) {
            this.roadNameBonbun = Integer.parseInt(roadNameBonbun);
        }
    }

    public Integer getRoadNameBubun() {
        return roadNameBubun;
    }

    public void setRoadNameBubun(String roadNameBubun) {
        if (roadNameBubun != null) {
            this.roadNameBubun = Integer.parseInt(roadNameBubun);
        }
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            id = 0;
        }
        this.id = id;
    }
}
