package com.jfcbxp.pagamento.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final JwtTokenProvider tokenProvider;

    @Autowired
    public JwtConfigure(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter tokenFilter = new JwtTokenFilter(tokenProvider);
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
