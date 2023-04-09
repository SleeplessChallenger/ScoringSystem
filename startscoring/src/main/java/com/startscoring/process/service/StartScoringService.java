package com.startscoring.process.service;

import com.startscoring.process.dto.Applicant;
import com.startscoring.process.dto.Deposit;
import com.startscoring.process.persistence.customer.ApplicantEntity;
import com.startscoring.process.persistence.customer.ApplicantRepository;
import com.startscoring.process.persistence.deposit.DepositEntity;
import com.startscoring.process.persistence.deposit.DepositRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class StartScoringService {

    private final ApplicantRepository applicantRepository;
    private final DepositRepository depositRepository;

    public void registerApplicant(Applicant applicant) {
        final ApplicantEntity applicantEntity = ApplicantEntity.builder()
                .firstName(applicant.getFirstName())
                .lastName(applicant.getLastName())
                .middleName(applicant.getMiddleName())
                .age(applicant.getAge())
                .build();
        applicantRepository.save(applicantEntity);
    }

    public void registerDeposit(Deposit deposit) {
        final DepositEntity depositEntity = DepositEntity.builder()
                .depositType(deposit.getDepositType())
                .depositPrice(deposit.getDepositPrice())
                .depositAge(deposit.getDepositAge())
                .build();
        depositRepository.save(depositEntity);
    }
}
