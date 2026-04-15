package com.rohit.clinic.dto.response;

import com.rohit.clinic.entity.MatchSide;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultResponse {

    private Long marketListingId;
    private Long companyId;
    private String companyName;
    private String contactPerson;
    private String phone;
    private String city;
    private String state;
    private Long productId;
    private String productName;
    private MatchSide matchSide;
    private BigDecimal minQuantity;
    private BigDecimal maxQuantity;
    private String unitText;
    private BigDecimal rateHint;
    private Integer priorityRank;

    public Long getMarketListingId() {
        return marketListingId;
    }

    public void setMarketListingId(Long marketListingId) {
        this.marketListingId = marketListingId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public MatchSide getMatchSide() {
        return matchSide;
    }

    public void setMatchSide(MatchSide matchSide) {
        this.matchSide = matchSide;
    }

    public BigDecimal getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getUnitText() {
        return unitText;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public BigDecimal getRateHint() {
        return rateHint;
    }

    public void setRateHint(BigDecimal rateHint) {
        this.rateHint = rateHint;
    }

    public Integer getPriorityRank() {
        return priorityRank;
    }

    public void setPriorityRank(Integer priorityRank) {
        this.priorityRank = priorityRank;
    }
}
