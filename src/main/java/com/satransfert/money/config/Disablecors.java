package com.satransfert.money.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class Disablecors extends WebSecurityConfig  {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable();
    }
}
