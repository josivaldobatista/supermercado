package com.jfb.supermercado.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfb.supermercado.api.domain.PagamentoComBoleto;
import com.jfb.supermercado.api.domain.PagamentoComCartao;

/**
 * Essa classe de configuração é padrão para todas as vezes que vc
 * precisar permitir a instanciação de subclasses a partir de dados JSON
 * o que vai mudar é nas classes que vc vai registrar no caso abaixo
 * as classes PagamentoComCartao e PagamentoComBoleto.
 */

@Configuration
public class JacksonConfig {
    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}