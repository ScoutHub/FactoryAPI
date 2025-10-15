package com.vaudoise.factory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @NotNull(message = "Cost amount is required")
    @Positive(message = "Cost amount must be positive")
    @Column(nullable = false)
    private float costAmount;

    @Column(nullable = false)
    private LocalDate updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    public Contract() {}

    public Contract(LocalDate startDate, LocalDate endDate, float costAmount, Client client) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.costAmount = costAmount;
        this.client = client;
        this.updateDate = LocalDate.now();
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

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = LocalDate.now();
    }
}