package com.nardo.platform.security;

import org.springframework.stereotype.Service;

/**
 * Manages Role-Based Access Control.
 */
@Service
public class AccessController {

    public static final String RESOURCE_DASHBOARD = "ADMIN_DASHBOARD";
    public static final String RESOURCE_REPORTS = "STOCK_REPORTS";
    public static final String RESOURCE_ORDER = "ORDER_BOUNDARY";

    /**
     * Checks if the given role has permission to access the requested resource.
     * 
     * @param role The user's role (e.g., ADMIN, CUSTOMER)
     * @param resourceType The resource being accessed
     * @return true if access is permitted, false otherwise
     */
    public boolean hasAccess(String role, String resourceType) {
        // Admins can access everything including sensitive data
        if ("ADMIN".equals(role)) {
            return true;
        }

        // Customers can only access order functionalities
        if ("CUSTOMER".equals(role)) {
            return RESOURCE_ORDER.equals(resourceType);
        }

        // Default to denied
        return false;
    }
}
