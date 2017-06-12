package com.olegv.dashboard;

import com.google.inject.Inject;
import com.olegv.user.User;
import com.olegv.user.UserService;
import com.olegv.util.Path;
import com.olegv.util.RequestUtil;
import com.olegv.util.ViewUtil;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.olegv.user.UserRole.getUserRoleHomepage;

public class LoginController {
    
    public static class LoginPageHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            Map<String, Object> model = new HashMap<>();
            model.put("loggedOut", RequestUtil.removeSessionAttrLoggedOut(request));
            model.put("loginRedirect", RequestUtil.removeSessionAttrLoginRedirect(request));
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
    }
    
    public static class LoginRequestHandler implements Route {
        @Inject
        private UserService userService;
        
        @Override
        public Object handle(Request request, Response response) throws Exception {
            Map<String, Object> model = new HashMap<>();
            Optional<User> user = userService.authenticateAndGet(RequestUtil.getQueryUsername(request), RequestUtil.getQueryPassword(request));
            if (!user.isPresent()) {
                model.put("authenticationFailed", true);
                return ViewUtil.render(request, model, Path.Template.LOGIN);
            }
            model.put("authenticationSucceeded", true);
            request.session().attribute("currentUser", user.get().getUsername());
            request.session().attribute("currentUserRole", user.get().getRole());
            if (RequestUtil.getQueryLoginRedirect(request) != null) {
                response.redirect(RequestUtil.getQueryLoginRedirect(request));
            } else {
                response.redirect(getUserRoleHomepage(user.get().getRole()));
            }
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
    }
    
    public static class LogoutRequestHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            request.session().removeAttribute("currentUser");
            request.session().removeAttribute("currentUserRole");
            request.session().attribute("loggedOut", true);
            response.redirect(Path.Web.LOGIN);
            return null;
        }
    }
    
    // The origin of the request (request.pathInfo()) is saved in the session so the users can be redirected back after login
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Path.Web.LOGIN);
        }
    }
    
    public static void ensureUserIsLoggedInWithRole(Request request, Response response, String role) {
        ensureUserIsLoggedIn(request, response);
        if (role == null) return;
        String currentUserRole = request.session().attribute("currentUserRole");
        if (currentUserRole == null || !currentUserRole.equalsIgnoreCase(role)) {
            Spark.halt(HttpStatus.NOT_FOUND_404, ViewUtil.render(request, new HashMap<>(), Path.Template.NOT_FOUND));
        }
    }
    
}
