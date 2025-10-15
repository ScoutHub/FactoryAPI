package com.vaudoise.factory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERSON")
public class Person extends Client {

    @NotNull(message = "Birthdate is required")
    @Column(updatable = false)
    private LocalDate birthdate;

    public Person() {
        super();
    }

    public Person(String name, String email, String phone, LocalDate birthdate) {
        super(name, email, phone);
        this.birthdate = birthdate;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}