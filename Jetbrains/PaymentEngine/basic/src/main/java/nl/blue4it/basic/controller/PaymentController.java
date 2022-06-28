package nl.blue4it.basic.controller;

import example.avro.Payment;
import lombok.RequiredArgsConstructor;

import nl.blue4it.basic.kafka.producer.PaymentProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentProducer paymentProducer;

    @PostMapping
    public ResponseEntity<String> processPayment() {
        if(paymentProducer.processPayment(createPayment(), "payments")) {
            return ResponseEntity.ok("Created payment");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Payment createPayment() {
        // 2.1 create a payment
        return null;
    }
}
