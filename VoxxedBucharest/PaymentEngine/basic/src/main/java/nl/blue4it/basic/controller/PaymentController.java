package nl.blue4it.basic.controller;

import example.avro.Payment;
import lombok.RequiredArgsConstructor;

import nl.blue4it.basic.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> processPayment() {
        if(paymentService.processPayment(createPayment())) {
            return ResponseEntity.ok("Created payment");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Payment createPayment() {
        // 1 Create Avro Payment and Fraud
        // 2.1 create a Payment
        return null;
    }
}
