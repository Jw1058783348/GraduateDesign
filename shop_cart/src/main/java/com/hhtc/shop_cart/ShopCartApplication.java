package com.hhtc.shop_cart;

import com.hhtc.aop.LoginAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopCartApplication.class, args);
    }

    @Bean
    public LoginAop getAop(){
        return new LoginAop();
    }

}
