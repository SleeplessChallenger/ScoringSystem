package com.initialchecks.process.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "check_application")
@NoArgsConstructor
@AllArgsConstructor
public class CheckEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "check_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "check_id_sequence"
    )
    private Integer id;

    @Column(name = "applicant_id", nullable = false, unique = true)
    private String applicantId;
    @Column(name = "deposit_id", nullable = false, unique = true)
    private String depositId;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
