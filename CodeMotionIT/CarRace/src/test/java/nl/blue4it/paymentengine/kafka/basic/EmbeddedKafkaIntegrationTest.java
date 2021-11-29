package nl.blue4it.paymentengine.kafka.basic;

import nl.blue4it.paymentengine.OwnYourSoftwareApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@SpringBootTest(classes = OwnYourSoftwareApplication.class)
@KafkaListener(topics = "payments", groupId = "something")
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
        Thread.sleep(5000);
        System.out.println("--------> payment received with payload " + consumer.getRecord());
        assertEquals("transaction 12345678", consumer.getRecord());
    }
}
