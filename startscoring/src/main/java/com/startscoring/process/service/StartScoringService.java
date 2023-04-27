package com.startscoring.process.service;

import com.feign.clients.initialchecks.ApplicationRequest;
import com.feign.clients.initialchecks.InitialCheckClient;
import com.scoring.commons.utils.UuidUtils;
import com.startscoring.process.dto.Applicant;
import com.startscoring.process.dto.Deposit;
import com.startscoring.process.persistence.customer.ApplicantEntity;
import com.startscoring.process.persistence.customer.ApplicantRepository;
import com.startscoring.process.persistence.deposit.DepositEntity;
import com.startscoring.process.persistence.deposit.DepositRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StartScoringService {

    private final ApplicantRepository applicantRepository;
    private final DepositRepository depositRepository;
    private final InitialCheckClient initialCheckClient;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ApplicantEntity registerApplicant(Applicant applicant) {
        final LocalDateTime now = LocalDateTime.now();
        final ApplicantEntity applicantEntity = ApplicantEntity.builder()
                .applicantSystemId(applicant.getId())
                .firstName(applicant.getFirstName())
                .lastName(applicant.getLastName())
                .middleName(applicant.getMiddleName())
                .age(applicant.getAge())
                .createdAt(now)
                .updatedAt(now)
                .build();

        applicantRepository.save(applicantEntity);
        log.info("Saved applicant with id = {}", applicant.getId());
        return applicantEntity;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void registerDeposit(Deposit deposit, ApplicantEntity applicant) {
        final LocalDateTime now = LocalDateTime.now();
        final DepositEntity depositEntity = DepositEntity.builder()
                .depositSystemId(deposit.getDepositId())
                .depositType(deposit.getDepositType())
                .depositPrice(deposit.getDepositPrice())
                .depositAge(deposit.getDepositAge())
                .createdAt(now)
                .updatedAt(now)
                .applicant(applicant)
                .build();
        depositRepository.save(depositEntity);

        applicant.setDeposits(List.of(depositEntity));
        log.info("Saved deposit with id = {}", deposit.getDepositId());
    }

    public void startInitialChecks(String applicantId, String depositId) {
        final String requestId = UuidUtils.generateUuid();
        initialCheckClient.checkApplication(requestId,
                ApplicationRequest.builder()
                        .applicantId(applicantId)
                        .depositId(depositId)
                        .build());
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateInteractions(String applicantId, String depositId) {
        final LocalDateTime now = LocalDateTime.now();
        applicantRepository.updateApplicantInteraction(now, applicantId);
        depositRepository.updateDepositInteraction(now, depositId);
    }
}
