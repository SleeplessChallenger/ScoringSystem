package com.finalchecks.service;

import com.finalchecks.persistence.applicant.ApplicantDecisionEntity;
import com.finalchecks.persistence.applicant.ApplicantDecisionRepository;
import com.finalchecks.persistence.deposit.DepositDecisionEntity;
import com.finalchecks.persistence.deposit.DepositDecisionRepository;
import com.scoring.commons.dto.kafka.ApplicantDto;
import com.scoring.commons.dto.kafka.DepositDto;
import com.scoring.commons.enums.SentStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DecisionService {

    private final DepositDecisionRepository depositDecisionRepository;
    private final ApplicantDecisionRepository applicantDecisionRepository;


    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void persistApplicantDecision(ApplicantDto applicant) {
        final ApplicantDecisionEntity applicantDecision = ApplicantDecisionEntity.builder()
                .applicantSystemId(applicant.getApplicantId())
                .decisionMadeAt(applicant.getDecisionAtTime())
                .finaDecision(applicant.getDecision().name())
                .sentStatus(SentStatus.NOT_SENT.name())
                .build();
        applicantDecisionRepository.save(applicantDecision);
        log.info("Persisted data about decision for applicant = {}", applicant.getApplicantId());
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void persistDepositDecision(DepositDto deposit) {
        final DepositDecisionEntity depositDecision = DepositDecisionEntity.builder()
                .depositSystemId(deposit.getDepositId())
                .decisionMadeAt(deposit.getDecisionAtTime())
                .finalDecision(deposit.getDecision().name())
                .sentStatus(SentStatus.NOT_SENT.name())
                .build();
        depositDecisionRepository.save(depositDecision);
        log.info("Persisted data about decision for deposit = {}", deposit.getDepositId());
    }
}
