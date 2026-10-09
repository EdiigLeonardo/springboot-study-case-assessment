package com.devoteam.payments.solid.paymentMethods;
import com.devoteam.payments.solid.PaymentMethod;

import org.springframework.stereotype.Component;

@Component
public class DebitCardPayment implements PaymentMethod {
    @Override
    public double calculateFee(double amount) {
        return amount * 0.01;
    }
}