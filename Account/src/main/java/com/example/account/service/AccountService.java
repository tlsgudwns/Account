package com.example.account.service;


import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.dto.AccountDto;
import com.example.account.exception.AccountException;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AccountUserRepository;
import com.example.account.type.AccountStatus;
import com.example.account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.account.type.AccountStatus.IN_USE;
import static com.example.account.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    //이렇게 final을 붙이면 이 값은 생성자에 의해서만 담을수있다
    //내가 만들 빈에 다른 빈을 넣어주고싶을때 이런식으로
    //final을 붙이고 @RequiredArgsConstructor을 붙이면
    //빈을 자동으로 주입받을수있다

    private final AccountUserRepository accountUserRepository;


    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance) {
        //사용자가 있는지 조회
        //계좌의 번호 생성하고
        //계좌를 저장하고 그 정보를 넘겨준다
        AccountUser accountUser = getAccountUser(userId);
        //userId가 accountUserRepository내에 없다면 에러발생되고 있다면
        //accountUser에 정보가 들어가게된다


        validateCreateAccount(accountUser);


        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc()
                .map(account -> (Integer.parseInt(account.getAccountNumber()) + 1 + ""))
                .orElse("1000000000");
                //findFirstByOrderByIdDesc로 가장 최근 생성된 account가져오고
        //그 account를 accountNumber를 받아서 그 문자열을 int로 바꾸고 1 더하고
        //다시 ""를 더해서 문자열로바꾼다
        //만약 이전계좌가 없다면 orElse가 작동되고 1000000000이 새 계좌번호가된다


        return AccountDto.fromEntity(
                accountRepository.save(
                Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(IN_USE)
                        .accountNumber(newAccountNumber)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build())
        ); //제일 안쪽 Account.builder()~로 account생성
          //생성된 account를 accountRepository.save하면 저장은 저장대로 되고
        //그 저장한 account를 또 반환을해주는데 그것을
        //AccountDto 클래스의 fromEntity해준다
        //fromEntity는 account를 인자로받고 그걸 AccountDto로 만들어서 반환해준다

    }

    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(ErrorCode.MAX_ACCOUNT_PER_USER_10);
        }
    }

    @Transactional
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("Minus");
        }

        return accountRepository.findById(id).get();
    }


    @Transactional
    public AccountDto deleteAccount(Long userId, String accountNumber) {
        AccountUser accountUser = getAccountUser(userId);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        // 윗 두 문장은 user와 계좌가 저장소에 저장되어있는지 확인하는코드

        validateDeleteAccount(accountUser, account);


        account.setAccountStatus(AccountStatus.UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());

        accountRepository.save(account);

        return AccountDto.fromEntity(account);
    }

    private void validateDeleteAccount(AccountUser accountUser, Account account) {
        if (!Objects.equals(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(USER_ACCOUNT_UN_MATCH);
            //사용자와 계좌가 매칭이 되는지 확인하는코드
        }

        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
            throw new AccountException(ACCOUNT_ALREADY_UNREGISTERED);
            //계좌가 이미해지된상태인지 체크
        }

        if (account.getBalance() > 0) {
            throw new AccountException(BALANCE_NOT_EMPTY);
        }
    }

    @Transactional
    public List<AccountDto> getAccountsByUserId(Long userId) {
        AccountUser accountUser = getAccountUser(userId);

        List<Account> accounts = accountRepository
                .findByAccountUser(accountUser);

        return accounts.stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    private AccountUser getAccountUser(Long userId) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
        return accountUser;
    }
}
