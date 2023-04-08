package com.startscoring.process.service;

import com.startscoring.process.dto.ApplicantData;
import com.startscoring.process.dto.Customer;
import com.startscoring.process.persistence.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class StartScoringService {

    private final CustomerRepository customerRepository;

    public void registerApplicant(ApplicantData applicantData) {
        final Customer customer = applicantData.getCustomer();
        customerRepository.save(customer);
    }
}
