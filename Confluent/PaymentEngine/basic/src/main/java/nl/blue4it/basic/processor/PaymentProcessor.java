package nl.blue4it.basic.processor;

import example.avro.Payment;
import lombok.extern.slf4j.Slf4j;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessor {
    private Payment payment;

    // 3.1 create kafka listener with topics payments and groupid something
    @KafkaListener(topics = "payments", groupId = "something")
    public void consume(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.value());

        // 3.2 receiving payment (casting) and set it
        Payment payment = (Payment) consumerRecord.value();
        setPayment(payment);
    }

    public Payment getPayment() {
        return payment;
    }

    private void setPayment(Payment payment) {
        this.payment = payment;
    }
}
