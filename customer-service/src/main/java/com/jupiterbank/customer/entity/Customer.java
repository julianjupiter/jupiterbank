package com.jupiterbank.customer.entity;

import com.jupiterbank.customer.util.CustomerId;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
@Entity
public class Customer {
    @Id
    private Long id;
    @Convert(converter = CustomerIdConverter.class)
    private CustomerId customerId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String extensionName;
    private LocalDate dateOfBirth;
    private String contactNumber;
    private String emailAddress;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public Customer setId(Long id) {
        this.id = id;
        return this;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Customer setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Customer setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public Customer setExtensionName(String extensionName) {
        this.extensionName = extensionName;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Customer setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Customer setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Customer setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Customer setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Customer setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
