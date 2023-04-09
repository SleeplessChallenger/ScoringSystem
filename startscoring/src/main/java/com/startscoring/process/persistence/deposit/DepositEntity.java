package com.startscoring.process.persistence.deposit;

import com.startscoring.process.persistence.customer.ApplicantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DepositEntity {

    @Id
    @SequenceGenerator(name = "deposit_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_id_sequence")
    private Integer id;

    private String depositType;
    private Integer depositPrice;
    private Integer depositAge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private ApplicantEntity applicant;
}