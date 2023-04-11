package com.startscoring.process.service;

import com.feign.clients.initialchecks.ApplicationRequest;
import com.feign.clients.initialchecks.InitialCheckClient;
import com.startscoring.process.dto.Applicant;
import com.startscoring.process.dto.Deposit;
import com.startscoring.process.persistence.customer.ApplicantEntity;
import com.startscoring.process.persistence.customer.ApplicantRepository;
import com.startscoring.process.persistence.deposit.DepositEntity;
import com.startscoring.process.persistence.deposit.DepositRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class StartScoringService {

    private final ApplicantRepository applicantRepository;
    private final DepositRepository depositRepository;
    private final RestTemplate restTemplate;
    private final InitialCheckClient initialCheckClient;

    public ApplicantEntity registerApplicant(Applicant applicant) {
        // TODO: think about Id
        final ApplicantEntity applicantEntity = ApplicantEntity.builder()
                .applicantSystemId(applicant.getId())
                .firstName(applicant.getFirstName())
                .lastName(applicant.getLastName())
                .middleName(applicant.getMiddleName())
                .age(applicant.getAge())
                .createdAt(LocalDateTime.now())
                .build();

        applicantRepository.save(applicantEntity);
        log.info("Saved applicant with id = {}", applicant.getId());
        return applicantEntity;
    }

    public void registerDeposit(Deposit deposit, ApplicantEntity applicant) {
        final DepositEntity depositEntity = DepositEntity.builder()
                .depositSystemId(deposit.getDepositId())
                .depositType(deposit.getDepositType())
                .depositPrice(deposit.getDepositPrice())
                .depositAge(deposit.getDepositAge())
                .createdAt(LocalDateTime.now())
                .applicant(applicant)
                .build();
        depositRepository.save(depositEntity);
        log.info("Saved deposit with id = {}", deposit.getDepositId());
    }

    public void startInitialChecks(String applicantId, String depositId) {
        // TODO: handle exceptions and further action to the database
        // TODO: persist data that current user is being processed

        final String requestId = UUID.randomUUID().toString();
        final ResponseEntity<String> response = initialCheckClient.checkApplication(requestId,
                ApplicationRequest.builder()
                        .applicantId(applicantId)
                        .depositId(depositId)
                        .build());
    }
}
