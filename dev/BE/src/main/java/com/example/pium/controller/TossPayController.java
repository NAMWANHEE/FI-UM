package com.example.pium.controller;

import com.example.pium.config.TossPaymentConfig;
import com.example.pium.config.WebMvcConfig;
import com.example.pium.dto.PaymentSuccessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/toss")
@RestController
@Slf4j
public class TossPayController {
    private final TossPaymentConfig tossPaymentConfig;


    @GetMapping("success")
    public ResponseEntity<?> paymentResult(@RequestParam(value = "orderId") String orderId, @RequestParam(value = "paymentKey") String paymentKey, @RequestParam(value = "amount") int amount, @RequestParam(value = "paymentType") String paymentType) throws IOException, JSONException {

        Map<String,String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("amount",String.valueOf(amount));
        params.put("paymentKey",paymentKey);
        System.out.println(params);
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTESTSECRETKEY() + ":").getBytes(StandardCharsets.UTF_8)));
        System.out.println(encodedAuthKey);
        WebClient.create(tossPaymentConfig.getURL()).post().headers(httpHeaders -> {
            httpHeaders.setBasicAuth(encodedAuthKey);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        }).body(BodyInserters.fromValue(params)).retrieve().bodyToMono(PaymentSuccessDto.class).subscribe(
                responseBody -> {
                    System.out.println(responseBody);
                }
        );

//        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params,headers);
//        params.put("orderId",orderId);
//        params.put("amount",amount);
//        PaymentSuccessDto result = null;
//        try{
//            result = restTemplate.postForObject(tossPaymentConfig.getURL(), new HttpEntity<>(params,headers),PaymentSuccessDto.class);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(result);

        return ResponseEntity.ok("GOOD");
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



