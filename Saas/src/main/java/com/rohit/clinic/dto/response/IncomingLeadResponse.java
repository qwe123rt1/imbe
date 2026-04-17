package com.rohit.clinic.dto.response;

import com.rohit.clinic.entity.InterestedSide;
import com.rohit.clinic.entity.LeadStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingLeadResponse {

    private Long id;
    private Long tenantId;
    private String leadNo;
    private Long whatsappMessageId;
    private String leadType;
    private String partyName;
    private String contactPerson;
    private String phone;
    private String whatsappNo;
    private String rawMessage;
    private String productText;
    private BigDecimal quantity;
    private String unitText;
    private String locationFrom;
    private String locationTo;
    private InterestedSide interestedSide;
    private LeadStatus leadStatus;
    private Integer parseConfidence;
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getLeadNo() {
        return leadNo;
    }

    public void setLeadNo(String leadNo) {
        this.leadNo = leadNo;
    }

    public Long getWhatsappMessageId() {
        return whatsappMessageId;
    }

    public void setWhatsappMessageId(Long whatsappMessageId) {
        this.whatsappMessageId = whatsappMessageId;
    }

    public String getLeadType() {
        return leadType;
    }

    public void setLeadType(String leadType) {
        this.leadType = leadType;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
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

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
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

    public InterestedSide getInterestedSide() {
        return interestedSide;
    }

    public void setInterestedSide(InterestedSide interestedSide) {
        this.interestedSide = interestedSide;
    }

    public LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }

    public Integer getParseConfidence() {
        return parseConfidence;
    }

    public void setParseConfidence(Integer parseConfidence) {
        this.parseConfidence = parseConfidence;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
