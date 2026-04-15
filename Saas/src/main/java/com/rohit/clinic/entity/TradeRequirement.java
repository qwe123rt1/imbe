package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trade_requirements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequirement extends BaseEntity {

    @Column(name = "requirement_no", length = 100)
    private String requirementNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private IncomingLead lead;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_type", length = 50)
    private RequirementType requirementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "buyer_or_seller", length = 50)
    private MatchSide buyerOrSeller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_text")
    private String productText;

    @Column(name = "quantity", precision = 18, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_text", length = 50)
    private String unitText;

    @Column(name = "quality_spec")
    private String qualitySpec;

    @Column(name = "target_price", precision = 18, scale = 2)
    private BigDecimal targetPrice;

    @Column(name = "source_location")
    private String sourceLocation;

    @Column(name = "destination_location")
    private String destinationLocation;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "urgency_level", length = 100)
    private String urgencyLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_status", length = 50)
    private RequirementStatus requirementStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vertical_id")
    private Vertical vertical;

    @Column(name = "remarks")
    private String remarks;
}
