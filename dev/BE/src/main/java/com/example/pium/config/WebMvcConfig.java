package com.example.pium.config;

import com.example.pium.util.JwtTokenProvider;
import com.example.pium.util.TokenCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;



    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new TokenCheckInterceptor(jwtTokenProvider))
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/image/**")

                .excludePathPatterns("/user/signup")
                .excludePathPatterns("/user/check-id")
                .excludePathPatterns("/user/reissue")
                .excludePathPatterns("/auction/detail/**")
                .excludePathPatterns("/ranking")
                //.excludePathPatterns("/funding")
                .excludePathPatterns("/news")
                .excludePathPatterns("/edu")
               // .excludePathPatterns("/sponsorship")
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/stock")
                .excludePathPatterns("/stock/news")
                .excludePathPatterns("/stock/news/**")
                .excludePathPatterns("/stock/king")
                .excludePathPatterns("/stock/trade/**")
                .excludePathPatterns("/stock/chart/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/swagger-resources/**");

    }
}
