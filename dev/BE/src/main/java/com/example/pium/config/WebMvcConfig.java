package com.example.pium.config;

import com.example.pium.service.UserServiceImp;
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
    private final UserServiceImp userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new TokenCheckInterceptor(jwtTokenProvider,userService))
                .addPathPatterns("/donation")
                .addPathPatterns("/user/logout")
                .addPathPatterns("/user")
                .addPathPatterns("/user/total-capital")
                .addPathPatterns("/user/capital/{userNo}")
                .addPathPatterns("/user/deposit-saving")
                .addPathPatterns("/user/rival")
                .addPathPatterns("/user/child")
                .addPathPatterns("/user/profile-image")
                .addPathPatterns("/auction")
                .addPathPatterns("/auction/bid/{auctionNo}")
                .addPathPatterns("/auction/{auctionNo}")
                .addPathPatterns("/auction/purchase")
                .addPathPatterns("/bank/deposit")
                .addPathPatterns("/bank/checkPrime")
                .addPathPatterns("/funding/{itemNo}")
                .addPathPatterns("/bank/bank-info")
                .addPathPatterns("/funding/progress")
                .addPathPatterns("/funding/myFunding")
                .addPathPatterns("/funding/record")
                .addPathPatterns("/sponsorship/support/{itemNo}")
                .addPathPatterns("/sponsorship")
                .addPathPatterns("/sponsorship/{itemNo}")
                .addPathPatterns("/sponsorship/record/detail")
                .addPathPatterns("/sponsorship/record")
                .addPathPatterns("/image")
                .addPathPatterns("/toss/success/**")
                .addPathPatterns("/following")
                .addPathPatterns("/point")
                .addPathPatterns("/bank/saving")
                .addPathPatterns("/stock/my-stock")
                .addPathPatterns("/stock/my-account/{stock_no}")
                .addPathPatterns("/stock/buying")
                .addPathPatterns("/stock/selling")
                .addPathPatterns("/quiz")
                .addPathPatterns("/reviews")
                .addPathPatterns("/reviews/{id}")
                .addPathPatterns("/stock/my-account");



    }
}
