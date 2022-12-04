package com.example.account.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {
    //client와 이 application간에 데이터를 주고받는데 최적화된 dto
    //dto를 정해진 용도로만 쓰기위해서 여러개를 만드는것
    private String accountNumber;
    private Long balance;



}
