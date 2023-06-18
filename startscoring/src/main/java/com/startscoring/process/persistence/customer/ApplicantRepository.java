package com.startscoring.process.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Integer> {

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE {h-schema}applicants SET updated_at = :updatedAt where applicant_system_id = :applicantId",
            nativeQuery = true)
    void updateApplicantInteraction(@Param("updatedAt") LocalDateTime updatedAt,
                                    @Param("applicantId") String applicantId);
}
