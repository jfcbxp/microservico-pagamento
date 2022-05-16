package com.jfcbxp.pagamento.config;


import com.jfcbxp.pagamento.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    VendaRepository vendaRepository;


    @Bean
    public void startBD() {

    }

}
