package com.rohit.clinic.dto.response;

import com.rohit.clinic.entity.InterestedSide;
import com.rohit.clinic.entity.ParseStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParsedLeadResponse {

    private InterestedSide interestedSide;
    private BigDecimal quantity;
    private String unitText;
    private String productText;
    private String locationFrom;
    private String locationTo;
    private Integer parseConfidence;
    private ParseStatus parseStatus;
    private String remarks;

    public InterestedSide getInterestedSide() {
        return interestedSide;
    }

    public void setInterestedSide(InterestedSide interestedSide) {
        this.interestedSide = interestedSide;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnitText() {
        return unitText;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public Integer getParseConfidence() {
        return parseConfidence;
    }

    public void setParseConfidence(Integer parseConfidence) {
        this.parseConfidence = parseConfidence;
    }

    public ParseStatus getParseStatus() {
        return parseStatus;
    }

    public void setParseStatus(ParseStatus parseStatus) {
        this.parseStatus = parseStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
