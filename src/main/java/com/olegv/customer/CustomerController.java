package com.olegv.customer;

import com.google.inject.Inject;
import com.olegv.dashboard.LoginController;
import com.olegv.user.UserRole;
import com.olegv.util.JsonUtil;
import com.olegv.util.Path;
import com.olegv.util.RequestUtil;
import com.olegv.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CustomerController {
    
    public static class CustomersPageHandler implements Route {
        @Inject
        private CustomerDao customerDao;
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            LoginController.ensureUserIsLoggedInWithRole(request, response, UserRole.ROOT);
            HashMap<String, Object> model = new HashMap<>();
            model.put("customers", customerDao.getAll());
            return ViewUtil.render(request, model, Path.Template.CONTROL_PANEL_CUSTOMERS);
        }
    }
    
    public static class CustomerDetailsPageHandler implements Route {
        @Inject
        private CustomerDao customerDao;
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            LoginController.ensureUserIsLoggedInWithRole(request, response, UserRole.ROOT);
            HashMap<String, Object> model = new HashMap<>();
            Optional<Customer> customer = customerDao.getByUID(RequestUtil.getPathParamUID(request));
            model.put("customer", customer.orElse(null));
            return ViewUtil.render(request, model, Path.Template.CONTROL_PANEL_CUSTOMER_DETAILS);
        }
    }
    
    public static class APIGetAllCustomersHandler implements Route {
        
        @Inject
        private CustomerDao customerDao;
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            List<Customer> all = customerDao.getAll();
            response.type("application/json");
            return JsonUtil.dataToJson(all);
        }
    }
    
}
