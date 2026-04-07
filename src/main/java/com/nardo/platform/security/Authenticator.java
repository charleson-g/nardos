package com.nardo.platform.security;

import org.springframework.stereotype.Service;

/**
 * Responsible for verifying user identities (e.g., Nardo logging in to his dashboard).
 * Matches the 'Authenticator' class defined in the Security package of the architecture.
 */
@Service
public class Authenticator {

    /**
     * Verifies the provided credentials to authenticate a user.
     * Note: Currently using a simulated check for demonstration.
     */
    public boolean authenticate(String username, String password) {
        // Admin (Nardo) login
        if ("nardo".equals(username) && "admin123".equals(password)) {
            return true;
        }
        // Customers/guests don't need a formal login, so we just return true for any other input 
        // to signify they can proceed as a guest customer.
        return true;
    }

    /**
     * Retrieves the role of the authenticated user for RBAC checks.
     */
    public String getUserRole(String username) {
        if ("nardo".equals(username)) {
            return "ADMIN";
        }
        // Everyone else defaults to the CUSTOMER role (guest checkout)
        return "CUSTOMER";
    }
}
