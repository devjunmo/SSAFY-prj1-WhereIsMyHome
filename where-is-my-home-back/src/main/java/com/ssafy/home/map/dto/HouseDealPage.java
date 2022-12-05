package com.ssafy.home.map.dto;

import java.util.List;

public class HouseDealPage {
    private List<HouseDealDto> deals;
    private Boolean hasNextData = true;
    private Long cur;

    public List<HouseDealDto> getDeals() {
        return deals;
    }

    public void setDeals(List<HouseDealDto> deals) {
        this.deals = deals;
    }

    public Boolean getHasNextData() {
        return hasNextData;
    }

    public void setHasNextData(Boolean nextDataExist) {
        hasNextData = nextDataExist;
    }

    public Long getCur() {
        return cur;
    }

    public void setCur(Long cur) {
        this.cur = cur;
    }
}
