package com.yourBookStore.book_Service.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class appconfig {
    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}

    @Bean
    public RestClient restClient(){return RestClient.builder().build();}
}
