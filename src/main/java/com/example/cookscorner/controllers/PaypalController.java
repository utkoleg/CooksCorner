package com.example.cookscorner.controllers;

import com.example.cookscorner.services.PaypalService;
import com.example.cookscorner.services.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner/payment")
public class PaypalController {
    private final PaypalService paypalService;
    private final UserService userService;
    @GetMapping("/pay")
    public ResponseEntity<Object> pay() {
        try {
            Payment payment = paypalService.createPayment(
                    5.00,
                    "USD",
                    "paypal",
                    "sale",
                    "Subscription fee for one month",
                    "http://localhost:8080/cookscorner/payment/cancel",
                    "http://localhost:8080/cookscorner/payment/success"
            );
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(new URI(link.getHref()) + " " + payment.getId());
                }
            }
        } catch (PayPalRESTException | URISyntaxException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.badRequest().body("Unable to complete payment");
    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                userService.updateUserSubscriptionStatus(session, true);
                return ResponseEntity.ok("Subscription updated successfully.");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed.");
        }
        return ResponseEntity.badRequest().body("Payment not approved.");
    }

    @GetMapping("/cancel")
    public String paymentCancel(){
        return "Cancel";
    }
}
