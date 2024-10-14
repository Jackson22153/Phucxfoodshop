package com.phucx.phucxfoodshop.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paypal.base.rest.PayPalRESTException;
import com.phucx.phucxfoodshop.exceptions.NotFoundException;
import com.phucx.phucxfoodshop.exceptions.PaymentNotFoundException;
import com.phucx.phucxfoodshop.model.PaymentDTO;
import com.phucx.phucxfoodshop.model.PaymentResponse;

public interface PaymentService {
    public PaymentResponse createPayment(PaymentDTO paymentDTO) throws PayPalRESTException, JsonProcessingException, NotFoundException;
    public PaymentResponse updatePaymentByOrderIDAsSuccesful(String orderID) throws PaymentNotFoundException;
    public PaymentResponse updatePaymentByOrderIDAsCanceled(String orderID) throws PaymentNotFoundException;
}
