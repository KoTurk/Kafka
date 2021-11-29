package nl.blue4it.paymentengine.rest.controller;

import lombok.RequiredArgsConstructor;
import nl.blue4it.paymentengine.rest.domain.Account;
import nl.blue4it.paymentengine.rest.domain.Payment;
import nl.blue4it.paymentengine.rest.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final Account account;

    @GetMapping
    public ResponseEntity<Account> getAccountSettings() {
        return ResponseEntity.ok(account);
    }
}
