package com.olegv.util;

import com.olegv.user.UserRole;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Rendering view templates. Also class holds some request handlers without business logic, like 404 and 500 pages.
 */
public class ViewUtil {
    
    // Renders a template given a model and a request
    // The request is needed to check the users session for language settings
    // and to see if the users is logged in
    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("msg", new MessageBundle(RequestUtil.getSessionLocale(request)));
        model.put("currentUser", RequestUtil.getSessionCurrentUser(request));
        model.put("currentUserRole", RequestUtil.getSessionCurrentUserRole(request));
        model.put("WebPath", Path.Web.class);
        model.put("UserRole", UserRole.class);
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }
    
    public static Route notAcceptable = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_ACCEPTABLE_406);
        return new MessageBundle(RequestUtil.getSessionLocale(request)).get("ERROR_406_NOT_ACCEPTABLE");
    };
    
    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };
    
    public static Route internalServerError = (Request request, Response response) -> {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        return render(request, new HashMap<>(), Path.Template.INTERNAL_SERVER_ERROR);
    };
    
    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }
}
