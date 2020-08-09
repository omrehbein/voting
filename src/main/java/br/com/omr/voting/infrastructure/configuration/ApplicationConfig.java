package br.com.omr.voting.infrastructure.configuration;

import javax.persistence.EntityManagerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ApplicationConfig {

   @Bean
   public ModelMapper modelMapper() {
      ModelMapper modelMapper = new ModelMapper();
      return modelMapper;
   }
   
   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

     JpaTransactionManager txManager = new JpaTransactionManager();
     txManager.setEntityManagerFactory(entityManagerFactory);
     return txManager;
   }
}
