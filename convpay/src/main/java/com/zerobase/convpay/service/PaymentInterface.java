package com.zerobase.convpay.service;

import com.zerobase.convpay.type.CancelPaymentResult;
import com.zerobase.convpay.type.PayMethodType;
import com.zerobase.convpay.type.PaymentResult;

public interface PaymentInterface {
    //MoneyAdapter, CardAdapter가 이 PaymentInterface를 의존하도록 만들것이다
    PayMethodType getPayMethodType();
    PaymentResult payment(Integer payAmount);

    CancelPaymentResult cancelPayment(Integer cancelAmount);
}
