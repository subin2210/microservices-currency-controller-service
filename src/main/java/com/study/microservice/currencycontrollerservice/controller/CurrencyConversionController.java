package com.study.microservice.currencycontrollerservice.controller;

import com.study.microservice.currencycontrollerservice.bean.CurrencyConversion;
import com.study.microservice.currencycontrollerservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeProxy proxy;

    @GetMapping("/currency-controller/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calcuatedCurrencyConversion (
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
            ) {


        HashMap<String, String> uriVariables = new HashMap<String, String>();

        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConversion> currencyEntity =  new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}/",CurrencyConversion.class,
                uriVariables);

        CurrencyConversion currencyConversion = currencyEntity.getBody();

        return  new CurrencyConversion(currencyConversion.getId(),from,to,quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }

    @GetMapping("/currency-controller-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calcuatedCurrencyConversionFromFeign (
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from,to);

        return  new CurrencyConversion(currencyConversion.getId(),from,to,quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }
}
