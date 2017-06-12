package com.olegv.order;

import com.olegv.dashboard.LoginController;
import com.olegv.util.Path;
import com.olegv.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class OrderController {
    
    public static class OrdersPageHandler implements Route {
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            LoginController.ensureUserIsLoggedIn(request, response);
            Map<String, Object> model = new HashMap<>();
            return ViewUtil.render(request, model, Path.Template.CONTROL_PANEL_ORDERS);
        }
    }
    
}
