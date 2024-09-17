--liquibase formatted sql
--changeset julianjupiter:c0001 splitStatements:true endDelimiter:; context:dev,sit,uat,prod
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) NULL,
    extension_name VARCHAR(255) NULL,
    date_of_birth DATE NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) NULL,
    address VARCHAR(255) NOT NULL,
	created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_customer_id UNIQUE (customer_id),
    CONSTRAINT uq_customer_email_address UNIQUE (email_address)
);