package nl.blue4it.paymentengine.kafka.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private String record;

    @KafkaListener(topics = "payments", groupId = "something")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
            log.info("receiving something from our service");
            setRecord((String) consumerRecord.value());
    }

    public String getRecord() {
        return record;
    }

    private void setRecord(String value) {
        this.record = value;
    }

}
