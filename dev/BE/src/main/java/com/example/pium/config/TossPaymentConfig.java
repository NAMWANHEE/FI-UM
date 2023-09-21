package com.example.pium.config;

import lombok.Getter;
import lombok.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {

    private final String TESTCLIENTKEY = "test_ck_Z1aOwX7K8mevlzZnZPA8yQxzvNPG";

    private final String TESTSECRETKEY = "test_sk_PBal2vxj81Pv9NAPJgJr5RQgOAND";

    private final String SUCCESSURL = "http://j9a308.p.ssafy.io:8000/api/v1/toss/success";

    private final String FAILURL = "http://j9a308.p.ssafy.io:8000/api/v1/toss/fail";

    private final String URL = "https://api.tosspayments.com/v1/payments/confirm";
}
