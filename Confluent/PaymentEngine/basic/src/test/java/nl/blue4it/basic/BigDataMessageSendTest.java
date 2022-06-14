package nl.blue4it.basic;


import example.avro.Payment;
import nl.blue4it.basic.processor.PaymentProcessor;
import nl.blue4it.basic.service.PaymentService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext
@SpringBootTest(classes = PaymentEngineApplication.class)
@KafkaListener(topics = "payments", groupId = "something")
class BigDataMessageSendTest {

    @Autowired
    private PaymentService service;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test
    public void testConsumer() throws Exception {

        for(int i = 0; i < 1000; i++){
            service.processPayment(createPayment(), "payments");
        }


        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    assertThat(paymentProcessor.getPayment()).isNotNull();
                    assertTrue(paymentProcessor.getPayment().getProcessed());
                    System.out.println("--------> Payment send to topic, proceed");
                });
    }

    private Payment createPayment() {
        return Payment.newBuilder()
                .setName("Mister Blue")
                .setAmount(100.00F)
                .setBalance(500.00F)
                .setIban("NL63" + randomIbanCode() + "332454654")
                .setToIban("NL61RABO0332543675")
                .setProcessed(true)
                .build();
    }

    private String randomIbanCode() {
        String[] ibanCodes = {"RABO", "ABNA", "INGB", "DEUT"};
        Random random = new Random();
        int randomNumber = random.nextInt(ibanCodes.length);

        return ibanCodes[randomNumber];
    }

}