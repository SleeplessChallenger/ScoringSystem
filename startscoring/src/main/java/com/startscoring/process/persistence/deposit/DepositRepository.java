package com.startscoring.process.persistence.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE {h-schema}deposit SET updated_at = :updatedAt where deposit_system_id = :depositId",
            nativeQuery = true)
    void updateDepositInteraction(@Param("updatedAt") LocalDateTime updatedAt, @Param("depositId") String depositId);
}
