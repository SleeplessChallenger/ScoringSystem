package com.finalchecks.persistence.deposit;

import com.finalchecks.dto.DecisionResultDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepositDecisionRepository extends JpaRepository<DepositDecisionEntity, Integer> {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Query(value = "select " +
            "a.applicant_system_id as applicantSystemId, " +
            "d.deposit_system_id as depositSystemId, " +
            "a.applicant_final_decision as applicantDecision, " +
            "d.deposit_final_decision as depositDecision " +
            "from deposit_decision as d JOIN applicant_decision as a " +
            "ON a.id = d.applicant_decision_id " +
            "where d.deposit_final_decision = :sentStatus " +
            "and d.decision_made_at >= (NOW() - INTERVAL '12 hours') " +
            "and a.decision_made_at >= (NOW() - INTERVAL '12 hours') " +
            "FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<DecisionResultDto> findNotSentApplications(@Param("sentStatus") String sentStatus);

    // TODO: add liquibase and put there query with indexing DECISION row

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE {h-schema}deposit_decision SET sent = :sentStatus where deposit_system_id in :allSystemIds",
            nativeQuery = true)
    void updateDepositStatus(@Param("sentStatus") String sentStatus, @Param("allSystemIds") List<String> allSystemIds);

}
