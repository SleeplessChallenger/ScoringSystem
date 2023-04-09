package com.startscoring.process.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Integer> {
}

// TODO: write methods to take data if this applicant already has deposit