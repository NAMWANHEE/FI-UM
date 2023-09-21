package com.example.pium.controller;

import com.example.pium.config.TossPaymentConfig;
import com.example.pium.dto.PaymentSuccessDto;
import com.example.pium.entity.PointTypeEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.PointServiceImp;
import com.example.pium.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/toss")
@RestController
@Slf4j
public class TossPayController {
    private final TossPaymentConfig tossPaymentConfig;
    private final UserServiceImp userService;
    private final PointServiceImp pointService;

    @GetMapping("success")
    public ResponseEntity paymentResult(@RequestParam(value = "orderId") String orderId, @RequestParam(value = "paymentKey") String paymentKey, @RequestParam(value = "amount") int amount, HttpServletRequest request) {
        log.info("request to api/v1/toss/success  Method:[GET]");
        Integer userNo = (Integer) request.getAttribute("userNo");
        UserEntity user = userService.getUserInfo(userNo);
        Map<String,String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("amount",String.valueOf(amount));
        params.put("paymentKey",paymentKey);
        String msg = "";
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTESTSECRETKEY() + ":").getBytes(StandardCharsets.UTF_8)));

        Mono<PaymentSuccessDto> paymentSuccessDto = WebClient.create(tossPaymentConfig.getURL()).post().headers(httpHeaders -> {
                httpHeaders.setBasicAuth(encodedAuthKey);
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            }).body(BodyInserters.fromValue(params)).retrieve().bodyToMono(PaymentSuccessDto.class);
        try{
            PaymentSuccessDto savedPaymentDto = paymentSuccessDto.block();
            user.setCash(user.getCash()+amount);
            userService.save(user);
//            userService.updateBalanceSheetPoint(userNo,amount);
//            PointTypeEntity pointType = pointService.getPointType("캐시");
//            pointService.makePointRecord(user,pointType,amount);
            msg = "성공";
        }
        catch (Exception e){
            msg = "실패";
        }

            return ResponseEntity.ok(msg);






    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTESTSECRETKEY() + ":").getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}



