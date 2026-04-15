package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "whatsapp_incoming_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppIncomingMessage extends BaseEntity {

    @Column(name = "whatsapp_message_id")
    private String whatsappMessageId;

    @Column(name = "sender_phone", length = 50)
    private String senderPhone;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "message_text", columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    @Column(name = "processed_flag")
    private Boolean processedFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "parse_status", length = 50)
    private ParseStatus parseStatus;

    @Column(name = "parse_confidence")
    private Integer parseConfidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "extracted_side", length = 50)
    private InterestedSide extractedSide;

    @Column(name = "extracted_product_text")
    private String extractedProductText;

    @Column(name = "extracted_quantity", precision = 18, scale = 2)
    private BigDecimal extractedQuantity;

    @Column(name = "extracted_unit", length = 50)
    private String extractedUnit;

    @Column(name = "extracted_location_from")
    private String extractedLocationFrom;

    @Column(name = "extracted_location_to")
    private String extractedLocationTo;

    @Column(name = "remarks")
    private String remarks;

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
