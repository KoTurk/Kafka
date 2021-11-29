package nl.blue4it.paymentengine.rest.service;

import lombok.RequiredArgsConstructor;
import nl.blue4it.paymentengine.kafka.basic.PaymentProducer;
import nl.blue4it.paymentengine.rest.domain.Account;
import nl.blue4it.paymentengine.rest.domain.Payment;
import nl.blue4it.paymentengine.rest.exception.FraudDetectionException;
import nl.blue4it.paymentengine.rest.exception.NotEnoughBalanceException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentBalanceService paymentBalanceService;
    private final PaymentFraudService paymentFraudService;
    private final PaymentMessageService paymentMessageService;
    private final PaymentRewardService paymentRewardService;
    private final PaymentAnalyticsService paymentAnalyticsService;
    private final ProcessPaymentService processPaymentService;
    private final PaymentProducer producer;

    public boolean processPayment(Payment payment, Account account)
            throws NotEnoughBalanceException, FraudDetectionException {

        // check balance
        if (paymentBalanceService.getBalance() < payment.getAmount()) {
            throw new NotEnoughBalanceException();
        }

        producer.send("payments", "demo or some other object");

        // rewards
        producer.send("payments", "demo or some other object");

        // analytics
        producer.send("payments", "demo or some other object");

        // process payment
        if (payment.getCode == "OWN") {
            producer.send("payments", "demo or some other object");
        } else {
            processPaymentService.processPaymentToOtherBank();
        }

        // send alerts
        paymentMessageService.sendAlert("SUCCESS");

        return true;
    }

    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList(1);
        payments.add(new Payment());
        return payments;
    }

    public boolean cancelPayment(Payment payment) {
        return true;
    }
}