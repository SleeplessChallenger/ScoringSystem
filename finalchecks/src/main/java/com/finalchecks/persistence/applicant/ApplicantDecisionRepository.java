package com.finalchecks.persistence.applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicantDecisionRepository extends JpaRepository<ApplicantDecisionEntity, Integer> {

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE {h-schema}applicant_decision SET sent = :sentStatus " +
            "where applicant_system_id in :allSystemIds",
            nativeQuery = true)
    void updateApplicantStatus(@Param("sentStatus") String sentStatus,
                               @Param("allSystemIds") List<String> allSystemIds);

}
