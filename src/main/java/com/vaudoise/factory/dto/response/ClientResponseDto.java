package com.vaudoise.factory.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public class ClientResponseDto {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String clientType;

    private LocalDate birthdate;
    private String companyIdentifier;

    public ClientResponseDto() {}

    public ClientResponseDto(UUID id, String name, String email, String phone, String clientType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.clientType = clientType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }
}
