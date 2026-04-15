package com.rohit.clinic.dto.response;

import com.rohit.clinic.entity.InterestedSide;
import com.rohit.clinic.entity.ParseStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppIncomingMessageResponse {

    private Long id;
    private String whatsappMessageId;
    private String senderPhone;
    private String senderName;
    private String messageText;
    private LocalDateTime receivedAt;
    private Boolean processedFlag;
    private ParseStatus parseStatus;
    private Integer parseConfidence;
    private InterestedSide extractedSide;
    private String extractedProductText;
    private BigDecimal extractedQuantity;
    private String extractedUnit;
    private String extractedLocationFrom;
    private String extractedLocationTo;
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhatsappMessageId() {
        return whatsappMessageId;
    }

    public void setWhatsappMessageId(String whatsappMessageId) {
        this.whatsappMessageId = whatsappMessageId;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Boolean getProcessedFlag() {
        return processedFlag;
    }

    public void setProcessedFlag(Boolean processedFlag) {
        this.processedFlag = processedFlag;
    }

    public ParseStatus getParseStatus() {
        return parseStatus;
    }

    public void setParseStatus(ParseStatus parseStatus) {
        this.parseStatus = parseStatus;
    }

    public Integer getParseConfidence() {
        return parseConfidence;
    }

    public void setParseConfidence(Integer parseConfidence) {
        this.parseConfidence = parseConfidence;
    }

    public InterestedSide getExtractedSide() {
        return extractedSide;
    }

    public void setExtractedSide(InterestedSide extractedSide) {
        this.extractedSide = extractedSide;
    }

    public String getExtractedProductText() {
        return extractedProductText;
    }

    public void setExtractedProductText(String extractedProductText) {
        this.extractedProductText = extractedProductText;
    }

    public BigDecimal getExtractedQuantity() {
        return extractedQuantity;
    }

    public void setExtractedQuantity(BigDecimal extractedQuantity) {
        this.extractedQuantity = extractedQuantity;
    }

    public String getExtractedUnit() {
        return extractedUnit;
    }

    public void setExtractedUnit(String extractedUnit) {
        this.extractedUnit = extractedUnit;
    }

    public String getExtractedLocationFrom() {
        return extractedLocationFrom;
    }

    public void setExtractedLocationFrom(String extractedLocationFrom) {
        this.extractedLocationFrom = extractedLocationFrom;
    }

    public String getExtractedLocationTo() {
        return extractedLocationTo;
    }

    public void setExtractedLocationTo(String extractedLocationTo) {
        this.extractedLocationTo = extractedLocationTo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
