package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.MarkWhatsAppMessageFailedRequest;
import com.rohit.clinic.dto.request.MarkWhatsAppMessageParsedRequest;
import com.rohit.clinic.dto.request.SaveWhatsAppIncomingMessageRequest;
import com.rohit.clinic.dto.response.WhatsAppIncomingMessageResponse;
import com.rohit.clinic.entity.ParseStatus;
import com.rohit.clinic.entity.WhatsAppIncomingMessage;
import com.rohit.clinic.repository.WhatsAppIncomingMessageRepository;
import com.rohit.clinic.service.WhatsAppIncomingMessageService;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppIncomingMessageServiceImpl implements WhatsAppIncomingMessageService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppIncomingMessageServiceImpl.class);

    private final WhatsAppIncomingMessageRepository repository;

    public WhatsAppIncomingMessageServiceImpl(WhatsAppIncomingMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public WhatsAppIncomingMessageResponse saveIncomingMessage(SaveWhatsAppIncomingMessageRequest request) {
        String whatsappMessageId = request.getWhatsappMessageId();
        if (whatsappMessageId != null && !whatsappMessageId.isBlank()) {
            WhatsAppIncomingMessage existing = repository.findByWhatsappMessageId(whatsappMessageId)
                    .orElse(null);
            if (existing != null) {
                log.info("[LEAD][WHATSAPP] duplicate message reused id={} messageKey={}",
                        existing.getId(), whatsappMessageId);
                return toResponse(existing);
            }
        }

        WhatsAppIncomingMessage message = new WhatsAppIncomingMessage();
        message.setWhatsappMessageId(whatsappMessageId);
        message.setSenderPhone(request.getSenderPhone());
        message.setSenderName(request.getSenderName());
        message.setMessageText(request.getMessageText());
        message.setReceivedAt(LocalDateTime.now());
        message.setProcessedFlag(false);
        message.setParseStatus(ParseStatus.NOT_PROCESSED);
        WhatsAppIncomingMessage saved = repository.save(message);
        log.info("[LEAD][WHATSAPP] saved raw message id={} messageKey={}", saved.getId(), whatsappMessageId);
        return toResponse(saved);
    }

    @Override
    public WhatsAppIncomingMessageResponse markParsed(MarkWhatsAppMessageParsedRequest request) {
        WhatsAppIncomingMessage message = repository.findById(request.getMessageId())
                .orElseThrow(() -> new RuntimeException("WhatsApp message not found"));

        message.setProcessedFlag(true);
        message.setParseStatus(resolveParsedStatus(request.getParseConfidence()));
        message.setParseConfidence(request.getParseConfidence());
        return toResponse(repository.save(message));
    }

    @Override
    public WhatsAppIncomingMessageResponse markFailed(MarkWhatsAppMessageFailedRequest request) {
        WhatsAppIncomingMessage message = repository.findById(request.getMessageId())
                .orElseThrow(() -> new RuntimeException("WhatsApp message not found"));

        message.setProcessedFlag(false);
        message.setParseStatus(ParseStatus.FAILED);
        message.setRemarks(request.getRemarks());
        return toResponse(repository.save(message));
    }

    @Override
    public List<WhatsAppIncomingMessageResponse> getUnprocessedMessages() {
        return repository.findByProcessedFlagFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ParseStatus resolveParsedStatus(Integer parseConfidence) {
        if (parseConfidence != null && parseConfidence >= 80) {
            return ParseStatus.PARSED_HIGH_CONFIDENCE;
        }
        return ParseStatus.PARSED_LOW_CONFIDENCE;
    }

    private WhatsAppIncomingMessageResponse toResponse(WhatsAppIncomingMessage message) {
        WhatsAppIncomingMessageResponse response = new WhatsAppIncomingMessageResponse();
        response.setId(message.getId());
        response.setWhatsappMessageId(message.getWhatsappMessageId());
        response.setSenderPhone(message.getSenderPhone());
        response.setSenderName(message.getSenderName());
        response.setMessageText(message.getMessageText());
        response.setReceivedAt(message.getReceivedAt());
        response.setProcessedFlag(message.getProcessedFlag());
        response.setParseStatus(message.getParseStatus());
        response.setParseConfidence(message.getParseConfidence());
        response.setExtractedSide(message.getExtractedSide());
        response.setExtractedProductText(message.getExtractedProductText());
        response.setExtractedQuantity(message.getExtractedQuantity());
        response.setExtractedUnit(message.getExtractedUnit());
        response.setExtractedLocationFrom(message.getExtractedLocationFrom());
        response.setExtractedLocationTo(message.getExtractedLocationTo());
        response.setRemarks(message.getRemarks());
        return response;
    }
}
