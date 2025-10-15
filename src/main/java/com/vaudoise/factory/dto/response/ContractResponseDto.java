package com.vaudoise.factory.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public class ContractResponseDto {

    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private float costAmount;
    private UUID clientId;

    public ContractResponseDto() {}

    public ContractResponseDto(UUID id, LocalDate startDate, LocalDate endDate, float costAmount, UUID clientId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costAmount = costAmount;
        this.clientId = clientId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(float costAmount) {
        this.costAmount = costAmount;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}