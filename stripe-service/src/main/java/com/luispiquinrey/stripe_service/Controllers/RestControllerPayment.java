package com.luispiquinrey.stripe_service.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;



@RestController
public class RestControllerPayment {
    @PostMapping("/checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, Object> infoUser) {
        try {
            String price = (String) infoUser.get("price");
            String returnUrl = (String) infoUser.get("returnUrl");

            SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                .setReturnUrl(returnUrl)
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPrice(price)
                        .build()
                )
                .build();

            Session session = Session.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());
            responseData.put("clientSecret", session.getClientSecret());

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
