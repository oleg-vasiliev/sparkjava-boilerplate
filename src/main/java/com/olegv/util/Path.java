package com.olegv.util;

import lombok.Getter;

/**
 * Holds all urls and template paths in one place.
 */
@SuppressWarnings("WeakerAccess")
public class Path {
    
    // The @Getter methods are needed in orders to access the variables from Velocity Templates
    public static class Web {
        @Getter public static final String CONTROL_PANEL_DASHBOARD = "/controlpanel/";
        @Getter public static final String CONTROL_PANEL_ORDERS = "/controlpanel/orders/";
        @Getter public static final String CONTROL_PANEL_CUSTOMERS = "/controlpanel/customers/";
        @Getter public static final String CONTROL_PANEL_CUSTOMERS_ONE = "/controlpanel/customers/:uid/";
        @Getter public static final String CONTROL_PANEL_ACCOUNT = "/controlpanel/account/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String API_CUSTOMERS = "/api/customers/";
    }
    
    public static class Template {
        public final static String CONTROL_PANEL_DASHBOARD = "/velocity/controlpanel/dashboard.vm";
        public final static String CONTROL_PANEL_ORDERS = "/velocity/controlpanel/order/orders.vm";
        public final static String CONTROL_PANEL_CUSTOMERS = "/velocity/controlpanel/customer/customers.vm";
        public final static String CONTROL_PANEL_CUSTOMER_DETAILS = "/velocity/controlpanel/customer/customer_details.vm";
        public final static String CONTROL_PANEL_ACCOUNT = "/velocity/controlpanel/account.vm";
        public final static String LOGIN = "/velocity/login.vm";
        public static final String NOT_FOUND = "/velocity/404.vm";
        public static final String INTERNAL_SERVER_ERROR = "/velocity/500.vm";
    }
    
}
