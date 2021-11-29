package nl.blue4it.paymentengine.rest.controller;

import lombok.RequiredArgsConstructor;
import nl.blue4it.paymentengine.rest.domain.Account;
import nl.blue4it.paymentengine.rest.domain.Payment;
import nl.blue4it.paymentengine.rest.exception.FraudDetectionException;
import nl.blue4it.paymentengine.rest.exception.NotEnoughBalanceException;
import nl.blue4it.paymentengine.rest.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getPaymentTimeline() {
        return ResponseEntity.ok(paymentService.getPayments());
    }

    @PostMapping
    public ResponseEntity<String> processPayment(@Validated Payment payment, Account account)
            throws NotEnoughBalanceException, FraudDetectionException {
        if(paymentService.processPayment(payment, account)) {
            return ResponseEntity.ok("Created payment");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity cancelPayment(Payment payment) {
        if (paymentService.cancelPayment(payment)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
