package com.example.account.repository;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//account라는 테이블에 접속하기위한 인터페이스이다
    // <Account, Long>  활용할 엔티티와 엔티티이 primary key의 타입인 Long을 넣어여한다

    Optional<Account> findFirstByOrderByIdDesc();
    //이렇게 이름붙이면 가장 최근에 만들어진 account가 들어간다??


    Integer countByAccountUser(AccountUser accountUser);

    Optional<Account> findByAccountNumber(String AccountNumber);

    List<Account> findByAccountUser(AccountUser accountUser);
}
