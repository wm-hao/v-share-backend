package zhh.share.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zhh.share.interceptor.TokenInterceptor;

/**
 * @author richer
 * @date 2020/7/24 10:12 上午
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${originUrl}")
    String originUrl;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).excludePathPatterns("/index*", "/static/**", "/user/**", "/error*", "/css/**", "/js/**", "/fonts/**", "/img/**", "/*.ico").addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //两个*表示以/uploadFiles开始的任意层级的路径都可以访问得到图片，如<img src="../uploadFiles/img/1.png">
        //一个*表示只可以访问assets目录下的图片文件
        //addResourceHandler指的是访问路径，addResourceLocations指的是文件放置目录
        /*registry.addResourceHandler("/chargeFile/**").addResourceLocations("classpath:/chargeFile");*/

        registry.addResourceHandler("/img/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/img/");
        registry.addResourceHandler("/js/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/js/");
        registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/fonts/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/index.*").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/*.ico").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        config.addAllowedOrigin(originUrl);
        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);
        // 4）允许的头信息
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        //return new CorsFilter(configSource);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        bean.setOrder(0);
        return bean;
    }
}
