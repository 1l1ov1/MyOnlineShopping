package com.wan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wan.interceptor.JwtTokenInterceptor;
import com.wan.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;

    /**
     * 添加自定义拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                // 拦截所有路径
                .addPathPatterns("/**")
                // 不包括用户登录，注册，验证码，退出账号和 重置、忘记密码，分类的分页查询，商品的分页查询
                // 查看商品详情，收藏，搜索商品或商店，分类查询还有刷新token
                .excludePathPatterns("/user/login", "/user/register", "/user/verify",
                        "/user/userLogout", "/user/forgetPwd", "/category/page","/goods/query",
                        "/goods/detail","/goods/{id}","/favorite/search/{id}","/goods/query/{goodsName}",
                        "/store/query/{storeName}", "/store/getStore/{id}","/goods/category/{id}",
                        "/user/queryComment", "/user/queryCommentAction",
                        "/refresh/refreshToken");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:7070") // 替换为前端域名
                        .allowCredentials(true)
                        .allowedMethods("*")
                        .maxAge(3600);
            }
        };
    }

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket docket1() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("在线商城项目接口文档")
                .version("2.0")
                .description("在线商城项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wan.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


    /**
     * 配置消息转换器，用于支持JSON序列化和反序列化时的日期时间处理。
     * 这个方法覆盖了Spring MVC中默认的消息转换器配置，加入了对Java 8日期时间API的支持。
     *
     * @param converters 一个HttpMessageConverter类型的列表，用于将HTTP消息与对象之间进行转换。
     *                   这个参数允许我们向Spring MVC框架注册自定义的消息转换器，以处理特定的数据类型。
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("配置消息转换器...");
        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 需要为消息转换器设置对象转换器，对象转换器可以将json对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自己的消息转换器加入容器中（优先使用）
        converters.add(0, converter);
    }

}
