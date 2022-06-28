package nl.blue4it.basic.kafka.producer;

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
public class PaymentProducer {

    // 2.2 Create Kafka <String, Payment> Template

    public boolean processPayment(SpecificRecordBase payment, String topic) {
        // 2.3 send transaction

        return true;
    }

    private void sendTransaction(String topic, Account key, SpecificRecordBase message) {
        log.info("Going to process transaction, sending message {}", message);

        // 2.4.2 send exception

        // 2.4 Send to topic "payments" and send message
    }
}