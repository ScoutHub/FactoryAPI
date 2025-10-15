package com.vaudoise.factory.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CompanyRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid (10-15 digits)")
    private String phone;

    @NotBlank(message = "Company identifier is required")
    @Pattern(regexp = "^[A-Za-z]+-\\d{3}$", message = "Company identifier must follow the format: aaa-123")
    private String companyIdentifier;

    public CompanyRequestDto() {}

    public CompanyRequestDto(String name, String email, String phone, String companyIdentifier) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.companyIdentifier = companyIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }
}
