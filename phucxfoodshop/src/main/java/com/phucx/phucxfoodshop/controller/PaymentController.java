package com.phucx.phucxfoodshop.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.base.rest.PayPalRESTException;
import com.phucx.phucxfoodshop.exceptions.NotFoundException;
import com.phucx.phucxfoodshop.model.PaymentDTO;
import com.phucx.phucxfoodshop.model.PaymentMethod;
import com.phucx.phucxfoodshop.model.PaymentResponse;
import com.phucx.phucxfoodshop.service.payment.PaymentProcessorService;
import com.phucx.phucxfoodshop.service.paymentMethod.PaymentMethodService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private PaymentProcessorService paymentProcessorService;

    @Operation(summary = "Pay order", tags = {"private", "post", "customer"})
    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentDTO payment, 
        HttpServletRequest request, HttpServletResponse response
    ) throws PayPalRESTException, IOException, NotFoundException{
        String baseUrl = getBaseUrl(request);
        payment.setBaseUrl(baseUrl);
        PaymentResponse paymentResponse = paymentProcessorService.createPayment(payment);
        return ResponseEntity.ok().body(paymentResponse);
    }

    @Operation(summary = "Get payment methods", tags = {"private", "get"})
    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods(){
        List<PaymentMethod> methods = paymentMethodService.getPaymmentMethods();
        return ResponseEntity.ok().body(methods);
    }

    // get base url
    private String getBaseUrl(HttpServletRequest request){
        String uri = request.getRequestURI().toString();
        String url = request.getRequestURL().toString();
        String baseurl = url.substring(0, url.length()-uri.length());
        return baseurl;
    }
}
