package com.startscoring.process.persistence;

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
public class CustomerEntity {
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
}
