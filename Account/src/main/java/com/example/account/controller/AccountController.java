package com.example.account.controller;


import com.example.account.domain.Account;
import com.example.account.dto.AccountDto;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    //외부에서는 이 controller로만 접속하고 이 controller는
    //Service로 접속하고 Service는 Repository로 접속하는
    //순차적인 구조이다


    @PostMapping ("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request
    ) {
//        AccountDto accountDto = accountService.createAccount(
//                request.getUserId(),
//                request.getInitialBalance()
//        );  이걸 변수선언하지말고 직접 리턴문 전달인자안에 넣어줌
        return CreateAccount.Response.from(
                accountService.createAccount(
                        request.getUserId(),
                        request.getInitialBalance()
                )
        );
    }
    //이런식으로 create-account api를 만들면
    //url에 create-account가 들어오면
    //이 createAccount함수가 호출이되고 이 내부에있는
    //accountService의 createAccount가 호출된다
    //그러면 account 엔티티를 생성하고 그 엔티티를
    //accountRepository에 save하게된다




    @DeleteMapping ("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request
    ) {

        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                )
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId
    ) {
        return accountService.getAccountsByUserId(userId)
                .stream().map(accountDto ->
                        AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());
    }






    @GetMapping("/account/{id}") //여기서 들어오는 {id}를
    public Account getAccount(      //PathVariable로 받아서 인자로넣기
            @PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
