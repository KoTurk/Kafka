package nl.blue4it.basic.service;

import example.avro.Payment;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    // 2.2 Create Kafka <String, Paymemnt> Template
    private final KafkaTemplate<String, Payment> kafkaTemplate;

    public boolean processPayment(Payment payment) {
        // Check balance
        // Call to Fraud detection
        // Processing payment
        // Rewarding
        // Analytics
        // Sending alerts

        // 2.3 send transaction
        sendTransaction("payments", payment);

        // 2.5 do balance and reward check

        return true;
    }



    private void sendTransaction(String topic, Payment message) {
        log.info("Going to process transaction, sending message {}", message);
        // 2.4 Send to topic "payments" and send message
        kafkaTemplate.send(topic, message);
    }

    private void doFraudCheck(String topic, Payment message) {}
    private void doBalanceCheck(String topic, Payment message) {}
    private void rewardCustomer(String topic, Payment message) {}
}