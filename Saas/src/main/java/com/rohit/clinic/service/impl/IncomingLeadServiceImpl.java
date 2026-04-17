package com.rohit.clinic.service.impl;

import com.rohit.clinic.dto.request.CreateIncomingLeadFromWhatsAppRequest;
import com.rohit.clinic.dto.request.ParseWhatsAppMessageRequest;
import com.rohit.clinic.dto.response.IncomingLeadResponse;
import com.rohit.clinic.dto.response.ParsedLeadResponse;
import com.rohit.clinic.entity.IncomingLead;
import com.rohit.clinic.entity.LeadSource;
import com.rohit.clinic.entity.LeadStatus;
import com.rohit.clinic.entity.Product;
import com.rohit.clinic.entity.WhatsAppIncomingMessage;
import com.rohit.clinic.exception.BadRequestException;
import com.rohit.clinic.exception.NotFoundException;
import com.rohit.clinic.repository.IncomingLeadRepository;
import com.rohit.clinic.repository.LeadSourceRepository;
import com.rohit.clinic.repository.ProductRepository;
import com.rohit.clinic.repository.WhatsAppIncomingMessageRepository;
import com.rohit.clinic.service.IncomingLeadService;
import com.rohit.clinic.service.LeadParsingService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncomingLeadServiceImpl implements IncomingLeadService {

    private static final String WHATSAPP_SOURCE_CODE = "WHATSAPP";

    private final IncomingLeadRepository incomingLeadRepository;
    private final WhatsAppIncomingMessageRepository whatsAppIncomingMessageRepository;
    private final ProductRepository productRepository;
    private final LeadSourceRepository leadSourceRepository;
    private final LeadParsingService leadParsingService;

    public IncomingLeadServiceImpl(
            IncomingLeadRepository incomingLeadRepository,
            WhatsAppIncomingMessageRepository whatsAppIncomingMessageRepository,
            ProductRepository productRepository,
            LeadSourceRepository leadSourceRepository,
            LeadParsingService leadParsingService
    ) {
        this.incomingLeadRepository = incomingLeadRepository;
        this.whatsAppIncomingMessageRepository = whatsAppIncomingMessageRepository;
        this.productRepository = productRepository;
        this.leadSourceRepository = leadSourceRepository;
        this.leadParsingService = leadParsingService;
    }

    @Override
    @Transactional
    public IncomingLeadResponse createFromWhatsApp(CreateIncomingLeadFromWhatsAppRequest request) {
        if (request == null || request.getWhatsappMessageId() == null) {
            throw new BadRequestException("whatsappMessageId is required");
        }

        WhatsAppIncomingMessage message = whatsAppIncomingMessageRepository.findById(request.getWhatsappMessageId())
                .orElseThrow(() -> new NotFoundException("WhatsApp message not found"));

        Optional<IncomingLead> existingLead = incomingLeadRepository.findByWhatsappMessage_Id(message.getId());
        if (existingLead.isPresent()) {
            return toResponse(existingLead.get());
        }

        ParsedLeadResponse parsed = leadParsingService.parseMessage(buildParseRequest(message.getMessageText()));

        IncomingLead lead = new IncomingLead();
        lead.setLeadNo(generateLeadNo());
        lead.setTenant(message.getTenant());
        lead.setSource(findOrCreateWhatsAppSource());
        lead.setWhatsappMessage(message);
        lead.setLeadType("WHATSAPP_TEXT");
        lead.setPartyName(message.getSenderName());
        lead.setContactPerson(message.getSenderName());
        lead.setPhone(message.getSenderPhone());
        lead.setWhatsappNo(message.getSenderPhone());
        lead.setRawMessage(message.getMessageText());
        lead.setInterestedSide(parsed.getInterestedSide());
        lead.setProductText(parsed.getProductText());
        lead.setProduct(findProduct(parsed.getProductText()).orElse(null));
        lead.setQuantity(parsed.getQuantity());
        lead.setUnitText(parsed.getUnitText());
        lead.setLocationFrom(parsed.getLocationFrom());
        lead.setLocationTo(parsed.getLocationTo());
        lead.setLeadStatus(LeadStatus.NEW);
        lead.setParseConfidence(parsed.getParseConfidence());
        lead.setRemarks(parsed.getRemarks());

        IncomingLead saved = incomingLeadRepository.save(lead);

        message.setProcessedFlag(true);
        message.setParseStatus(parsed.getParseStatus());
        message.setParseConfidence(parsed.getParseConfidence());
        message.setExtractedSide(parsed.getInterestedSide());
        message.setExtractedProductText(parsed.getProductText());
        message.setExtractedQuantity(parsed.getQuantity());
        message.setExtractedUnit(parsed.getUnitText());
        message.setExtractedLocationFrom(parsed.getLocationFrom());
        message.setExtractedLocationTo(parsed.getLocationTo());
        whatsAppIncomingMessageRepository.save(message);

        return toResponse(saved);
    }

    private ParseWhatsAppMessageRequest buildParseRequest(String text) {
        ParseWhatsAppMessageRequest request = new ParseWhatsAppMessageRequest();
        request.setMessageText(text);
        return request;
    }

    private Optional<Product> findProduct(String productText) {
        if (productText == null || productText.isBlank()) {
            return Optional.empty();
        }
        return productRepository.findByProductNameIgnoreCaseAndIsActiveTrue(productText);
    }

    private LeadSource findOrCreateWhatsAppSource() {
        return leadSourceRepository.findBySourceCodeIgnoreCase(WHATSAPP_SOURCE_CODE)
                .orElseGet(this::createWhatsAppSource);
    }

    private LeadSource createWhatsAppSource() {
        LeadSource source = new LeadSource();
        source.setSourceName("WhatsApp");
        source.setSourceCode(WHATSAPP_SOURCE_CODE);
        source.setIsActive(true);
        return leadSourceRepository.save(source);
    }

    private String generateLeadNo() {
        String leadNo;
        do {
            leadNo = "LEAD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (incomingLeadRepository.existsByLeadNo(leadNo));
        return leadNo;
    }

    private IncomingLeadResponse toResponse(IncomingLead lead) {
        IncomingLeadResponse response = new IncomingLeadResponse();
        response.setId(lead.getId());
        response.setTenantId(lead.getTenant() != null ? lead.getTenant().getId() : null);
        response.setLeadNo(lead.getLeadNo());
        response.setWhatsappMessageId(lead.getWhatsappMessage() != null ? lead.getWhatsappMessage().getId() : null);
        response.setLeadType(lead.getLeadType());
        response.setPartyName(lead.getPartyName());
        response.setContactPerson(lead.getContactPerson());
        response.setPhone(lead.getPhone());
        response.setWhatsappNo(lead.getWhatsappNo());
        response.setRawMessage(lead.getRawMessage());
        response.setProductText(lead.getProductText());
        response.setQuantity(lead.getQuantity());
        response.setUnitText(lead.getUnitText());
        response.setLocationFrom(lead.getLocationFrom());
        response.setLocationTo(lead.getLocationTo());
        response.setInterestedSide(lead.getInterestedSide());
        response.setLeadStatus(lead.getLeadStatus());
        response.setParseConfidence(lead.getParseConfidence());
        response.setRemarks(lead.getRemarks());
        return response;
    }
}
