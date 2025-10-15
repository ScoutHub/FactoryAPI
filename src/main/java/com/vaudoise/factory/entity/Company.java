package com.vaudoise.factory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("COMPANY")
public class Company extends Client {

    @NotBlank(message = "Company identifier is required")
    @Pattern(regexp = "^[A-Za-z]+-\\d{3}$", message = "Company identifier must follow the format: aaa-123")
    @Column(updatable = false, unique = true)
    private String companyIdentifier;

    public Company() {
        super();
    }

    public Company(String name, String email, String phone, String companyIdentifier) {
        super(name, email, phone);
        this.companyIdentifier = companyIdentifier;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }
}