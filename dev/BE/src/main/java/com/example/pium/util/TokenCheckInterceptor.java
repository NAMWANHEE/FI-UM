package com.example.pium.util;

import com.example.pium.exception.InterceptorException;
import com.example.pium.exception.InterceptorExceptionEnum;
import com.example.pium.repository.UserRepository;
import com.example.pium.service.UserServiceImp;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TokenCheckInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImp userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        String [] arr = request.getRequestURI().split("/");
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }
        if(arr[arr.length-1].equals("auction") && request.getMethod().equals("GET")){
            return true;
        };
        if(arr[arr.length-1].equals("sponsorship") && request.getMethod().equals("GET")){
            return true;
        };
        if(arr[arr.length-1].equals("funding") && request.getMethod().equals("GET")){
            return true;
        };
        String jwtToken = jwtTokenProvider.getJwt();
        String uri = request.getRequestURI();
        if (uri.contains("swagger") || uri.contains("api-docs") || uri.contains("webjars")) {
            return true;
        }

        log.info("요청 주소 :" + request.getRequestURI());
        if(jwtToken != null){
            try {
                if(jwtTokenProvider.validateToken(jwtToken)) { // JWT 토큰이 유효하면
                    int userNo = jwtTokenProvider.getUserNo(jwtToken);
                    int userType = userService.getUserInfo(userNo).getUserType();
                    log.info("유효한 ACCESS-TOKEN | 유저 타입 : "+userType + " 유저 번호 : "+ userNo);
                    request.setAttribute("userType",userType);
                    request.setAttribute("userNo",userNo);
                    return HandlerInterceptor.super.preHandle(request, response, handler);
                }
                throw new InterceptorException(InterceptorExceptionEnum.UNAUTHORIZED);
                //throw new InterceptorException(InterceptorExceptionEnum.UNAUTHORIZED);
            } catch (MalformedJwtException e) { // 위조 시도

                throw new InterceptorException(InterceptorExceptionEnum.COUNTERFEIT);

            } catch (ExpiredJwtException e) { // 만료된 토큰
                log.error("ACCESS-TOKEN 만료");
                throw new InterceptorException(InterceptorExceptionEnum.UNAUTHORIZED);

            }
        } else { // 토큰이 없음

            if(uri.contains("/token")) { // 토큰 발급
                return true;
            }
            log.error("권한(토큰) 없음!!!! ");
            throw new InterceptorException(InterceptorExceptionEnum.UNAUTHORIZED);
        }




    }

}
