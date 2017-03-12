package com.metafour.mtrak.router;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
public class MtrakRouterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtrakRouterApplication.class, args);
	}

	@Bean
	public Docket mTrakRouterApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/\\{site\\}.*|/logs.*"))
				.build()
				.pathMapping("/")
				.directModelSubstitute(Calendar.class, String.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("mTrak Router Api")
				.description("For mTrak Android App")
				.contact(new Contact("Md. Noor Mohammad Siddique", null, "noor.siddique@metafour.com"))
				.version("1.0")
				.build();
	}
}
