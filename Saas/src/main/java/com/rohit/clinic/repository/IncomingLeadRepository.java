package com.rohit.clinic.repository;

import com.rohit.clinic.entity.IncomingLead;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingLeadRepository extends JpaRepository<IncomingLead, Long> {

    boolean existsByLeadNo(String leadNo);

    Optional<IncomingLead> findByWhatsappMessage_Id(Long whatsappMessageId);
}
