package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "requirement_matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequirementMatch extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private TradeRequirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matched_company_id", nullable = false)
    private Company matchedCompany;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_side", length = 50)
    private MatchSide matchSide;

    @Column(name = "match_score")
    private Integer matchScore;

    @Column(name = "priority_rank")
    private Integer priorityRank;

    @Column(name = "shortlisted_flag")
    private Boolean shortlistedFlag;

    @Column(name = "remarks")
    private String remarks;
}
