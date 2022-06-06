package nl.blue4it.basic.service;

import example.avro.Payment;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import nl.blue4it.basic.exception.FilterException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    // 2.2 Create Kafka <String, Payment> Template

    public boolean processPayment(Payment payment) {
        // Check balance
        // Call to Fraud detection
        // Processing payment
        // Rewarding
        // Analytics
        // Sending alerts

        // 2.3 send transaction

        // 2.5 do balance and reward check

        return true;
    }

    private void sendTransaction(String topic, Payment message) {
        log.info("Going to process transaction, sending message {}", message);

        // 2.4.2 send exception

        // 2.4 Send to topic "payments" and send message
    }

    private void doFraudCheck(String topic, Payment message) {}
    private void doBalanceCheck(String topic, Payment message) {}
    private void rewardCustomer(String topic, Payment message) {}
}