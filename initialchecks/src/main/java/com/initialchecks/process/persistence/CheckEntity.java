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
@NoArgsConstructor
@AllArgsConstructor
public class CheckEntity {

    @Id
    @SequenceGenerator(name = "check_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "check_id_sequence"
    )
    private Integer id;

    private Integer applicantId;
    private Integer depositId;
    private LocalDateTime createdAt;
}
