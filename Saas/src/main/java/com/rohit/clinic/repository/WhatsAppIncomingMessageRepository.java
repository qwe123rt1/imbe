package com.rohit.clinic.repository;

import com.rohit.clinic.entity.WhatsAppIncomingMessage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatsAppIncomingMessageRepository extends JpaRepository<WhatsAppIncomingMessage, Long> {

    List<WhatsAppIncomingMessage> findByProcessedFlagFalse();

    Optional<WhatsAppIncomingMessage> findByWhatsappMessageId(String whatsappMessageId);

    Optional<WhatsAppIncomingMessage> findByTenant_IdAndWhatsappMessageId(Long tenantId, String whatsappMessageId);

    boolean existsByWhatsappMessageId(String whatsappMessageId);
}
