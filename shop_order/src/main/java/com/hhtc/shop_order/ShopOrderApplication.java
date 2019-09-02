package com.hhtc.shop_order;

import com.hhtc.aop.LoginAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOrderApplication.class, args);
    }
    @Bean
    public LoginAop getAop(){
        return new LoginAop();
    }
}
