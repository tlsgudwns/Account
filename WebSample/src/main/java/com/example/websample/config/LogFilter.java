package com.example.websample.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("Hello logFilter : " + Thread.currentThread());
        chain.doFilter(request, response); //컨트롤러의 코드실행인듯? 왜 함수 이름이 재귀적으로 똑같은건가?
        log.info("Bye logFilter : " + Thread.currentThread());

    }
}
