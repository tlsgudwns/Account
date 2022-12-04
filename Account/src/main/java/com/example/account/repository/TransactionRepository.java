package com.example.account.repository;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
    //스프링이 구현체를 자동으로 만들어주는 인터페이스이기때문에 그냥 이렇게만하고 사용하면된다
    Optional<Transaction> findByTransactionId(String transactionId);
}
