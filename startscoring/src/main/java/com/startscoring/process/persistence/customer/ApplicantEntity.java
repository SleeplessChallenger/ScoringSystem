package com.startscoring.process.persistence.customer;

import com.startscoring.process.persistence.deposit.DepositEntity;
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
@Table(name = "applicants")
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name = "customer_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    private Integer id;

    @Column(name = "applicant_system_id", nullable = false, unique = true)
    private String applicantSystemId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // mappedBy field should match the field in DepositEntity
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant")
    private List<DepositEntity> deposits;
}
