package nl.blue4it.paymentengine.rest.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFraudService {
    public boolean isNotValid() {
        return false;
    }
}
