package com.ai.cn.config;


import com.nft.cn.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${swagger.status}")
    private boolean status;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludes = new String[]{"/","/static/**","/service/**","/webjars/**"};
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**").excludePathPatterns(excludes);    //                @LoginRequired
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }


    @Bean
    public Docket createH5RestApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(status)
                .apiInfo(apiInfo())
                .groupName("h5")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ai.cn.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CanBox    ")
                .version("1.0")
                .build();
    }


    //  ResourceHandler
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}

