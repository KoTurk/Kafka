package nl.blue4it.paymentengine.rest.client;

import nl.blue4it.paymentengine.rest.domain.PaymentType;

public class SystemA {
    public PaymentType[] getPaymenTypes() {
        return PaymentType.values();
    }
}
