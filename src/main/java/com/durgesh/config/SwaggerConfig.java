package com.durgesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;



@EnableWebMvc
@Configuration
//@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer{      
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
	


	
	@Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.durgesh.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @SuppressWarnings("deprecation")
	private ApiInfo getApiInfo() {

        return new ApiInfoBuilder()
                .title("OrderManageMentsystem API Developed By Durgeshkumar Gupta")
                .description("OrderManageMentsystem  REST API")
                .version("1.0.0-SNAPSHOT")
                .contact("Durgeshkumar contact No. 2225436655")
               
                .license("All rights reserved to Durgeshkumar")
                .licenseUrl("https://www.Durgeshkumar.com/in/en")
                .build();
    }
}