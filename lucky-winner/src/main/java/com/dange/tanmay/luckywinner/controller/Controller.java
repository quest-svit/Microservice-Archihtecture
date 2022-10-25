package com.dange.tanmay.luckywinner.controller;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Random;

@RestController
public class Controller {

    @Value("${api-gateway.url}")
    public String apiGatewayUrl; //="http://localhost:8020";

    @Autowired
    RestTemplate restTemplate;


    @CircuitBreaker(name="customer-service", fallbackMethod = "customerFallback")
    @GetMapping("/winner")
    public String winner(){
        int totalCount =  Integer.parseInt(getCount());
        Random random=new Random();
        int rand = random.nextInt(totalCount);
        return fetchCustomerName(rand);
    }

    private String getCount(){
         return makeRestCall(apiGatewayUrl + "/count");
    }

    private String fetchCustomerName(int id){
         return makeRestCall(apiGatewayUrl + "/getId/" + id);
    }

    private String customerFallback(Throwable t){
        System.out.println("Falling back" + t.toString());
        return "Bob";
    }

    private String makeRestCall(String  url){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

    }

}
