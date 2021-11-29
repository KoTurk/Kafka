package nl.blue4it.paymentengine.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentConsumer {
    private String record;

    @KafkaListener(topics = "payments", groupId = "something")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.value());
        setRecord((String) consumerRecord.value());
    }

    public String getRecord() {
        return record;
    }

    private void setRecord(String record) {
        this.record = record;
    }
}