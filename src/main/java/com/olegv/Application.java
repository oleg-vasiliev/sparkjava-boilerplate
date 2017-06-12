package com.olegv;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.olegv.customer.CustomerController;
import com.olegv.dashboard.DashboardController;
import com.olegv.dashboard.LoginController;
import com.olegv.order.OrderController;
import com.olegv.user.UserController;
import com.olegv.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        CmdArgs cmdArgs = new CmdArgs(args);
        Integer cmdPort = cmdArgs.switchIntValue("-port", 4567);
        Integer port = Integer.parseInt(AppProperties.getEnvironmentVariable("PORT", cmdPort.toString()));
        
        AppConfig appConfig = new AppConfig(cmdArgs);
        Injector injector = Guice.createInjector(appConfig);
        
        // Configure Spark
        port(port);
        staticFiles.location("/public");
        // Static files caching is disabled by default
        // staticFiles.expireTime(600L);
        enableDebugScreen();
        
        // Set up before-filters (called before each request)
        Spark.before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);
        
        // Set up routes
        redirect.any("/", Path.Web.CONTROL_PANEL_DASHBOARD);
        
        get(Path.Web.CONTROL_PANEL_DASHBOARD, injector.getInstance(DashboardController.DashboardPageHandler.class));
        get(Path.Web.CONTROL_PANEL_CUSTOMERS, injector.getInstance(CustomerController.CustomersPageHandler.class));
        get(Path.Web.CONTROL_PANEL_CUSTOMERS_ONE, injector.getInstance(CustomerController.CustomerDetailsPageHandler.class));
        get(Path.Web.CONTROL_PANEL_ORDERS, injector.getInstance(OrderController.OrdersPageHandler.class));
        
        get(Path.Web.CONTROL_PANEL_ACCOUNT, injector.getInstance(UserController.UserProfilePageHandler.class));
        post(Path.Web.CONTROL_PANEL_ACCOUNT, injector.getInstance(UserController.UserProfileEditRequestHandler.class));
        
        get(Path.Web.API_CUSTOMERS, injector.getInstance(CustomerController.APIGetAllCustomersHandler.class));
        
        get(Path.Web.LOGIN, injector.getInstance(LoginController.LoginPageHandler.class));
        post(Path.Web.LOGIN, injector.getInstance(LoginController.LoginRequestHandler.class));
        get(Path.Web.LOGOUT, injector.getInstance(LoginController.LogoutRequestHandler.class));
        
        Spark.notFound(ViewUtil.notFound);
        internalServerError(ViewUtil.internalServerError);
        
        //Set up after-filters (called after each get/post)
        after("*", Filters.addGzipHeader);
    }
    
    
}
