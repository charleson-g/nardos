package com.nardo.platform.business.control;

/**
 * Interface for the Payment Adapter
 * Provides a stable interface to varying external bank APIs
 * This serves as a "Contract" that the business layer uses to talk to the banks.
 */

public interface PaymentGatewayAdapter {
    /**
     * Sends a secure authorization request to the bank.
     * @return True if the bank returns "Success", False otherwise.
     */

    boolean authorize(double amount, String paymentDetails);
}
