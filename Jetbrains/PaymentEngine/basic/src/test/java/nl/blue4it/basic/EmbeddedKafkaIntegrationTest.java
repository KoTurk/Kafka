package nl.blue4it.basic;


import static org.assertj.core.api.Assertions.assertThat;

import example.avro.Balance;
import example.avro.Payment;

import lombok.extern.slf4j.Slf4j;
import nl.blue4it.basic.kafka.consumer.PaymentConsumer;
import nl.blue4it.basic.kafka.producer.PaymentProducer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PaymentEngineApplication.class)
@Slf4j
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private PaymentProducer service;

    @Autowired
    private PaymentConsumer paymentConsumer;

    @Test
    public void testConsumer() throws Exception {
        service.processPayment(createPayment(), "payments");
        service.processPayment(createBalance(), "balance");

        Awaitility.await()
                .atMost(Duration.ofSeconds(20))
                .untilAsserted(() -> {
                    assertThat(paymentConsumer.getPayment()).isNotNull();
                    assertTrue(paymentConsumer.getPayment().getProcessed());
                    log.info("--------> Payment send to topic, proceed");
                   });
    }

    private Payment createPayment() {
        return Payment.newBuilder()
                .setName("Mister Blue")
                .setAmount(100.00F)
                .setBalance(500.00F)
                .setIban("NL63ABNA332454654")
                .setToIban("NL61RABO0332543675")
                .setProcessed(true)
                .build();
    }

    private Balance createBalance() {
        return Balance.newBuilder()
                .setAmount(500.00F)
                .setCurrency("EUR")
                .build();
    }

}
