package com.initialchecks.process.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<CheckEntity, Integer> {
}
