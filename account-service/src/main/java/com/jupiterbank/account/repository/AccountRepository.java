package com.jupiterbank.account.repository;

import com.jupiterbank.account.entity.Account;
import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(CustomerId customerId);

    Optional<Account> findByAccountNumber(AccountNumber accountNumber);
}
