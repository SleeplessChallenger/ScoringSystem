package com.startscoring.process.persistence.customer;

import com.startscoring.process.persistence.deposit.DepositEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantEntity {
    @Id
    @SequenceGenerator(name = "customer_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    private Integer id;

    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant")
    private List<DepositEntity> deposit;
}
