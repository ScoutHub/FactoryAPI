package com.vaudoise.factory.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public class ContractRequestDto {

    @NotNull(message = "Client ID is required")
    private UUID clientId;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Cost amount is required")
    @Positive(message = "Cost amount must be positive")
    private float costAmount;

    public ContractRequestDto() {}

    public ContractRequestDto(UUID clientId, LocalDate startDate, LocalDate endDate, float costAmount) {
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costAmount = costAmount;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
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
}

