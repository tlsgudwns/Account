package com.example.account.domain;


import com.example.account.type.AccountStatus;
import com.example.account.type.TransactionResultType;
import com.example.account.type.TransactionType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity     //@Entity를 붙이면 테이블과 1대1 매칭이된다
public class Transaction extends BaseEntity{      //***트랜잭션과 관련된 모든 변수들이 domain안에 다
                                //들어있는거인듯


    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionResultType transactionResultType;

    @ManyToOne  //n개의 transaction이 한 account에 연결될수있도록한다는뜻
    private Account account;
    private Long amount;
    private Long balanceSnapshot;

    private String transactionId;   //위의 그냥 id를쓰면 이게 pk니까 보안상 위험하니까
                                    //transactionId를 따로 만들어서 사용
    private LocalDateTime transactedAt;


}
