package com.rohit.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lead_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadActivity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private IncomingLead lead;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", length = 50)
    private ActivityType activityType;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "next_follow_up_at")
    private LocalDateTime nextFollowUpAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "done_by")
    private User doneBy;
}
