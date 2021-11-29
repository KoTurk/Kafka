package nl.blue4it.paymentengine.kafka.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String payload) {
        log.info("log something usefull");
        kafkaTemplate.send(topic, payload);
    }
}
