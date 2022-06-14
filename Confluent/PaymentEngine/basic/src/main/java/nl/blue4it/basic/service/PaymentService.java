package nl.blue4it.basic.service;

import example.avro.Account;
import example.avro.Payment;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import nl.blue4it.basic.exception.FilterException;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    // 2.2 Create Kafka <String, Payment> Template
    private final KafkaTemplate<Account, SpecificRecordBase> kafkaTemplate;

    public boolean processPayment(SpecificRecordBase payment, String topic) {
        // Check balance
        // Call to Fraud detection
        // Processing payment
        // Rewarding
        // Analytics
        // Sending alerts

        // 2.3 send transaction
        sendTransaction(topic, new Account("Mister Blue", "NL63ABNA332454654"), payment);
        // 2.5 do balance and reward check

        return true;
    }

    private void sendTransaction(String topic, Account key, SpecificRecordBase message) {
        log.info("Going to process transaction, sending message {}", message);

        // 2.4.2 send exception
        if (message instanceof Payment) {
            if (((Payment) message).getIban().equals("NL61EVIL0332546754")) {
                FilterException up = new FilterException("oh no");
                Counter.builder("a.message.exception")
                        .tag("exception", up.getMessage())
                        .register(Metrics.globalRegistry)
                        .increment();
                throw up;
            }
        }

        // 2.4 Send to topic "payments" and send message
        kafkaTemplate.send(topic, key, message);
    }

    private void doFraudCheck(String topic, Payment message) {}
    private void doBalanceCheck(String topic, Payment message) {}
    private void rewardCustomer(String topic, Payment message) {}
}