package com.phucx.phucxfoodshop.service.payment.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paypal.base.rest.PayPalRESTException;
import com.phucx.phucxfoodshop.exceptions.NotFoundException;
import com.phucx.phucxfoodshop.exceptions.PaymentNotFoundException;
import com.phucx.phucxfoodshop.model.PaymentDTO;
import com.phucx.phucxfoodshop.model.PaymentResponse;
import com.phucx.phucxfoodshop.service.payment.PaymentManagementService;
import com.phucx.phucxfoodshop.service.payment.PaymentProcessorService;
import com.phucx.phucxfoodshop.service.payment.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImp implements PaymentService{
    @Autowired
    private PaymentProcessorService paymentProcessorService;
    @Autowired
    private PaymentManagementService paymentManagementService;
    @Override
    public PaymentResponse createPayment(PaymentDTO paymentDTO) throws PayPalRESTException, JsonProcessingException, NotFoundException {
        log.info("createPayment(paymentDTO={})", paymentDTO);
        PaymentResponse paymentResponse = paymentProcessorService.createPayment(paymentDTO);
        return paymentResponse;
    }

    @Override
    public PaymentResponse updatePaymentByOrderIDAsSuccesful(String orderID) throws PaymentNotFoundException {
        log.info("updatePaymentByOrderIDAsSuccesful(orderID={})", orderID);
        Boolean result = this.paymentManagementService.updatePaymentAsSuccessfulByOrderIDPerMethod(orderID);
        PaymentResponse response = new PaymentResponse();
        response.setStatus(result);
        return response;
    }

    @Override
    public PaymentResponse updatePaymentByOrderIDAsCanceled(String orderID) throws PaymentNotFoundException {
        log.info("updatePaymentByOrderIDAsCanceled(orderID={})", orderID);
        Boolean result = this.paymentManagementService.updatePaymentAsCanceledByOrderIDPerMethod(orderID);
        PaymentResponse response = new PaymentResponse();
        response.setStatus(result);
        return response;
    }
    
}
