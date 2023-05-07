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
@Table(name = "deposit")
@NoArgsConstructor
@AllArgsConstructor
public class DepositEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name = "deposit_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "deposit_id_sequence"
    )
    private Integer id;

    @Column(name = "deposit_system_id", nullable = false, unique = true)
    private String depositSystemId;
    @Column(name = "deposit_type", nullable = false)
    private String depositType;
    @Column(name = "deposit_price", nullable = false)
    private BigDecimal depositPrice;
    @Column(name = "deposit_age", nullable = false)
    private Integer depositAge;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private ApplicantEntity applicant;
}
