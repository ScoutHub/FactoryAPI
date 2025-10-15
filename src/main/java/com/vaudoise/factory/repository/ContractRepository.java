package com.vaudoise.factory.repository;

import com.vaudoise.factory.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("SELECT c FROM Contract c WHERE c.client.id = :clientId AND (c.endDate IS NULL OR c.endDate > :currentDate)")
    List<Contract> findActiveContractsByClientId(@Param("clientId") UUID clientId, @Param("currentDate") LocalDate currentDate);

    @Query("SELECT c FROM Contract c WHERE c.client.id = :clientId AND (c.endDate IS NULL OR c.endDate > :currentDate) AND c.updateDate = :updateDate")
    List<Contract> findActiveContractsByClientIdAndUpdateDate(@Param("clientId") UUID clientId, @Param("currentDate") LocalDate currentDate, @Param("updateDate") LocalDate updateDate);

    @Query("SELECT COALESCE(SUM(c.costAmount), 0) FROM Contract c WHERE c.client.id = :clientId AND (c.endDate IS NULL OR c.endDate > :currentDate)")
    float sumActiveContractsCostByClientId(@Param("clientId") UUID clientId, @Param("currentDate") LocalDate currentDate);

    List<Contract> findByClientId(UUID clientId);
}
