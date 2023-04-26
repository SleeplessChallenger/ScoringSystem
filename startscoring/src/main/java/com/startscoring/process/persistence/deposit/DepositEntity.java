package com.startscoring.process.persistence.deposit;

import com.startscoring.process.persistence.customer.ApplicantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DepositEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "deposit_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "deposit_id_sequence"
    )
    private Integer id;

    @Column(name = "deposit_system_id")
    private String depositSystemId;
    @Column(name = "deposit_type")
    private String depositType;
    @Column(name = "deposit_price")
    private BigDecimal depositPrice;
    @Column(name = "deposit_age")
    private Integer depositAge;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private ApplicantEntity applicant;
}
