package com.zerobase.convpay.config;

import com.zerobase.convpay.ConvpayApplication;
import com.zerobase.convpay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


@Configuration
//@ComponentScan(basePackages = "com.zerobase.convpay")// 이렇게 최상위 패키지를 넣어주면 이 아래로 쫙 스캔한다는뜻
@ComponentScan(basePackageClasses = ConvpayApplication.class)
public class ApplicationConfig {

    @Autowired
    private ApplicationContext applicationContext;

    public void getResource() throws IOException {
        Resource resource = applicationContext.getResource("myTemplate.txt");

        System.out.println(resource.contentLength() + "");

    }


    //이 어플리케이션 전체의 설정등을 담당할것이다
    //인터페이스를 어떤 클래스가 구현할지 등등

}
