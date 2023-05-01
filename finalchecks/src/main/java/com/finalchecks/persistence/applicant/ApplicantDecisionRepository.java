package com.finalchecks.persistence.applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicantDecisionRepository extends JpaRepository<ApplicantDecisionEntity, Integer> {

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE {h-schema}applicant_decision SET sent = :sentStatus " +
            "where applicant_system_id in :allSystemIds",
            nativeQuery = true)
    void updateApplicantStatus(@Param("sentStatus") String sentStatus,
                               @Param("allSystemIds") List<String> allSystemIds);

    @Query(value = "select applicant_system_id " +
            "from {h-schema}applicant_decision as a " +
            "where a.applicant_system_id = :systemId", nativeQuery = true)
    Optional<String> findByApplicantSystemId(@Param("systemId") String systemId);
}
