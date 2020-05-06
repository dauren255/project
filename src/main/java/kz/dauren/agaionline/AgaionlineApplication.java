package kz.dauren.agaionline;import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.context.annotation.Bean;import org.springframework.security.authentication.AuthenticationManager;import springfox.documentation.builders.PathSelectors;import springfox.documentation.builders.RequestHandlerSelectors;import springfox.documentation.service.ApiInfo;import springfox.documentation.spi.DocumentationType;import springfox.documentation.spring.web.plugins.Docket;import springfox.documentation.swagger2.annotations.EnableSwagger2;import java.util.Collections;@SpringBootApplication@EnableSwagger2public class AgaionlineApplication {    public static void main(String[] args) {        SpringApplication.run(AgaionlineApplication.class, args);    }    @Bean    public Docket swaggerConfiguration(){        return new Docket(DocumentationType.SWAGGER_2)                .select()                .paths(PathSelectors.any())                .apis(RequestHandlerSelectors.basePackage("kz.dauren.agaionline"))                .build()                .apiInfo(apiDetails());    }    private ApiInfo apiDetails(){        return new ApiInfo(                "Agai Online API",                "Agai Online Website API for Final",                "1.0",                "https://www.google.com",                new springfox.documentation.service.Contact("Dauren Buribekov","https://www.google.com", "dauren.buribekov@gmail.com"),                "API Library License",                "https://www.google.com",                Collections.emptyList());    }}