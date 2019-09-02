package com.hhtc.shop_front;

import com.hhtc.aop.LoginAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopFrontApplication.class, args);
    }

    @Bean
    public LoginAop getAop(){
        return new LoginAop();
    }

}
