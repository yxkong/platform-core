package com.github.platform.core.web.configuration;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.web.configuration.properties.SwaggerProperties;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 自定义web配置
 *      1, swagger3集成,访问地址：http://127.0.0.1:8001/swagger-ui/index.html
 *      2，添加UTF-8的转化
 *      3, api版本转化
 *
 * @author: yxkong
 * @date: 2021/12/9 3:55 下午
 * @version: 1.0
 */
@EnableOpenApi
@Configuration
public class Swagger3Configuration implements WebMvcConfigurer{
    private final SwaggerProperties swaggerProperties;
    public Swagger3Configuration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }
    @Bean
    public Docket createRestApi() {
        /**
         * 构建并返回文档摘要信息
         */
        Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage("com.github.platform");
        if (StringUtils.isNotEmpty(swaggerProperties.getScanPackage())){
            String[] split = swaggerProperties.getScanPackage().split(",");
            for (String path : split) {
                predicate = predicate.or( RequestHandlerSelectors.basePackage(path));
            }
        }
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
            // 定义是否开启swagger，false为关闭，可以通过变量控制
            .enable(swaggerProperties.isShow())
            // 将api的元信息设置为包含在json ResourceListing响应中。
            .apiInfo(apiInfo())
            // 接口调试地址
            .host(swaggerProperties.getServiceUrl())
            // 选择哪些接口作为swagger的doc发布
            .select()
            //扫描所有有注解@ApiOperation
            //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            // .apis(RequestHandlerSelectors.any())
            //扫描指定包的swagger注解,支持多个
            .apis(predicate)
//                .paths(PathSelectors.any())
            .build()
            // 支持的通讯协议集合
            //.protocols(newHashSet("https", "http"))
            //// 设置安全模式，可以设置访问的token
            //.securitySchemes(securitySchemes())
            //// 授权信息全局应用
            //.securityContexts(securityContexts());
            .globalRequestParameters(getGlobalRequestParameters())
            .globalResponses(HttpMethod.GET, getGlobalResonseMessage())
            .globalResponses(HttpMethod.POST, getGlobalResonseMessage());
    }

    /**
     * 构建摘要信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    //生成全局通用参数
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("用户token")
                // .required(true)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }


    private List<Response> getGlobalResonseMessage() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("404").description("not found").build());
        return responseList;
    }

    /**
     * 修改路径
     * @return
     */
//    @Bean
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route(RequestPredicates.GET("/swagger-resources"),
//                request -> ServerResponse.temporaryRedirect(URI.create("/swagger-ui/index.html")).build());
//    }
    /**
     * 解决springboot2.6 和springfox不兼容问题  Failed to start bean ‘ documentationPluginsBootstrapper ‘ ; nested exception…
     * @return
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                  .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field= ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration
                            .excludePathPatterns("/swagger**/**")
                            .excludePathPatterns("/webjars/**")
                            .excludePathPatterns("*.html")
                            .excludePathPatterns("/v3/**")
                            .excludePathPatterns("/doc.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
