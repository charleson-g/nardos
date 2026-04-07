package com.nardo.platform.business.control;

import org.springframework.stereotype.Component;

/**
 * A simulated implementation of the PaymentGatewayAdapter.
 * This class handles the actual "handshake" logic without needing a real account
 */

@Component

public class WiPayMockAdapter implements PaymentGatewayAdapter {

    @Override
    public boolean authorize(double amount, String paymentDetails) {
        //Simulate a brief delay for the network handshake
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted during WiPay handshake delay");
        }

        System.out.println("External Gateway (WiPay): Authorizing $" + amount + "...");
        return true; // Return 'true' to simulate a successful "Sure Money" capture
    }
}