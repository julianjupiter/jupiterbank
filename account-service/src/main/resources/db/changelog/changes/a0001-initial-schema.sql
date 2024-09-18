--liquibase formatted sql
--changeset julianjupiter:a0001 splitStatements:true endDelimiter:; context:dev,sit,uat,prod
CREATE TABLE IF NOT EXISTS account (
    id BIGINT NOT NULL AUTO_INCREMENT,
    account_type VARCHAR(255) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    date_opened DATE NOT NULL,
    date_closed DATE NULL,
    account_status VARCHAR(255) NOT NULL,
	created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_account_account_number UNIQUE (account_number)
);