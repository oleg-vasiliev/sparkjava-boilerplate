package com.olegv.user;

import com.google.inject.Inject;
import com.olegv.dashboard.LoginController;
import com.olegv.util.Path;
import com.olegv.util.RequestUtil;
import com.olegv.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

public class UserController {
    
    public static class UserProfilePageHandler implements Route {
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            LoginController.ensureUserIsLoggedIn(request, response);
            HashMap<String, Object> model = new HashMap<>();
            return ViewUtil.render(request, model, Path.Template.CONTROL_PANEL_ACCOUNT);
        }
    }
    
    public static class UserProfileEditRequestHandler implements Route {
        
        @Inject
        private UserService userService;
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            HashMap<String, Object> model = new HashMap<>();
            
            LoginController.ensureUserIsLoggedIn(request, response);
            String username = RequestUtil.getSessionCurrentUser(request);
            String queryPasswordOld = RequestUtil.getQueryPasswordOld(request);
            String queryPasswordNew = RequestUtil.getQueryPasswordNew(request);
            
            if (queryPasswordNew == null || queryPasswordNew.length() < 8) {
                model.put("formMessage", "PASSWORD_TOO_SHORT");
            } else if (!userService.setPassword(username, queryPasswordOld, queryPasswordNew)) {
                model.put("formMessage", "INVALID_PASSWORD");
            } else {
                model.put("formMessage", "SAVED_SUCCESSFULLY");
            }
            
            return ViewUtil.render(request, model, Path.Template.CONTROL_PANEL_ACCOUNT);
        }
    }
}
