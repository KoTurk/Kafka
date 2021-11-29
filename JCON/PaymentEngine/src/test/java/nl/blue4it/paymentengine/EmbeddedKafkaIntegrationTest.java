package nl.blue4it.paymentengine;

import nl.blue4it.paymentengine.kafka.PaymentConsumer;
import nl.blue4it.paymentengine.kafka.PaymentEngineApplication;
import nl.blue4it.paymentengine.kafka.PaymentProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@SpringBootTest(classes = PaymentEngineApplication.class)
@KafkaListener(topics = "payment-consumer", groupId = "something")
class EmbeddedKafkaIntegrationTest {

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private PaymentConsumer consumer;

    @Autowired
    private PaymentProducer producer;

    @Value("payments")
    private String topic;

    @Test
    public void testConsumer() throws Exception {
        producer.send(topic, "transaction 12345678");
        Thread.sleep(1000);
        System.out.println("--------> payment received with payload " + consumer.getRecord());
        assertEquals("transaction 12345678", consumer.getRecord());
    }
}
