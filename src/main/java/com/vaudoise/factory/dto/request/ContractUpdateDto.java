package com.vaudoise.factory.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ContractUpdateDto {

    @NotNull(message = "Cost amount is required")
    @Positive(message = "Cost amount must be positive")
    private float costAmount;

    public ContractUpdateDto() {}

    public ContractUpdateDto(float costAmount) {
        this.costAmount = costAmount;
    }

    public float getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(float costAmount) {
        this.costAmount = costAmount;
    }
}
