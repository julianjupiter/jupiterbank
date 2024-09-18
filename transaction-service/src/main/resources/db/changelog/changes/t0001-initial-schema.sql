--liquibase formatted sql
--changeset julianjupiter:t0001 splitStatements:true endDelimiter:; context:dev,sit,uat,prod
CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT NOT NULL AUTO_INCREMENT,
    transaction_id VARCHAR(255) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    transaction_type ENUM('DEPOSIT', 'WITHDRAWAL') NOT NULL,
    amount DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
	created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_transaction_transaction_id UNIQUE (transaction_id)
);