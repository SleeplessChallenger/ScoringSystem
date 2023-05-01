package com.finalchecks.persistence.applicant;

import com.finalchecks.persistence.deposit.DepositDecisionEntity;
import com.scoring.commons.enums.Decision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "applicant_decision")
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDecisionEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "applicant_decision_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "applicant_decision_id")
    private Integer id;

    @Column(name = "applicant_final_decision")
    private String finaDecision;

    // System id refers to the id in the whole system
    @Column(name = "applicant_system_id")
    private String applicantSystemId;

    @Column(name = "sent")
    private String sentStatus;

    @Column(name = "decision_made_at")
    private LocalDateTime decisionMadeAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicantDecision")
    private List<DepositDecisionEntity> depositsDecision;
}
