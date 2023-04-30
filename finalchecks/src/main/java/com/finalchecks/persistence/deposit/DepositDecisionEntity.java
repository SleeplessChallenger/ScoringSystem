package com.finalchecks.persistence.deposit;

import com.finalchecks.persistence.applicant.ApplicantDecisionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "deposit_decision")
@NoArgsConstructor
@AllArgsConstructor
public class DepositDecisionEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "deposit_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "deposit_id_sequence"
    )
    private Integer id;

    @Column(name = "deposit_final_decision")
    private String finalDecision;

    // System id refers to the id in the whole system
    @Column(name = "deposit_system_id")
    private String depositSystemId;

    @Column(name = "sent")
    private String sentStatus;

    @Column(name = "decision_made_at")
    private LocalDateTime decisionMadeAt;

    @Column(name = "applicant_decision")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_decision_id", nullable = false)
    private ApplicantDecisionEntity applicantDecision;
}
