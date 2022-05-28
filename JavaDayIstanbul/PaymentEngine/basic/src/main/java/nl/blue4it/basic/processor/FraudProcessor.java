package nl.blue4it.basic.processor;

import example.avro.Fraud;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FraudProcessor {
    private Fraud fraud;

    // 3.3 create kafka listener topics blacklists, and groupID something
    @KafkaListener(topics = "blacklist", groupId = "something")

    public void consume(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.value());
        // 3.4 cast to fraud
        Fraud fraud = (Fraud) consumerRecord.value();
        setFraud(fraud);
    }

    public Fraud getFraud() {
        return fraud;
    }

    private void setFraud(Fraud fraud) {
        this.fraud = fraud;
    }
}
