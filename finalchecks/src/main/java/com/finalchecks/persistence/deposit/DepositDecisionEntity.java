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
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "deposit_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "deposit_id_sequence"
    )
    private Integer id;

    @Column(name = "deposit_final_decision", nullable = false)
    private String finalDecision;

    // System id refers to the id in the whole system
    @Column(name = "deposit_system_id", nullable = false)
    private String depositSystemId;

    @Column(name = "sent", nullable = false)
    private String sentStatus;

    @Column(name = "decision_made_at", nullable = false)
    private LocalDateTime decisionMadeAt;

    @Column(name = "flow_unique_id", nullable = false)
    private String flowId;

}
