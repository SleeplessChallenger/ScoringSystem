package com.finalchecks.persistence.applicant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "applicant_decision")
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDecisionEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "applicant_decision_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "applicant_decision_id")
    private Integer id;

    @Column(name = "applicant_final_decision", nullable = false)
    private String finaDecision;

    // System id refers to the id in the whole system
    @Column(name = "applicant_system_id", nullable = false)
    private String applicantSystemId;

    @Column(name = "sent", nullable = false)
    private String sentStatus;

    @Column(name = "decision_made_at", nullable = false)
    private LocalDateTime decisionMadeAt;

    @Column(name = "flow_unique_id", nullable = false)
    private String flowId;

}
