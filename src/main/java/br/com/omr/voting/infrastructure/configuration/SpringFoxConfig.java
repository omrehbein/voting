package br.com.omr.voting.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_12)  
          .select()                                  
          //.apis(RequestHandlerSelectors.any()) 
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
          .paths(PathSelectors.any())                          
          .build();                                           
    }
    

    @Bean
    UiConfiguration uiConfig() {
      return UiConfigurationBuilder.builder()
          .docExpansion(DocExpansion.LIST) // or DocExpansion.NONE or DocExpansion.FULL
          .build();
    } 
}