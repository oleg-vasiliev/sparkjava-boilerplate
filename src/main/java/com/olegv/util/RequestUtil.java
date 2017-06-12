package com.olegv.util;

import spark.Request;

@SuppressWarnings("WeakerAccess")
public class RequestUtil {
    
    public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }
    
    public static String getPathParamUID(Request request) {
        return request.params("uid");
    }
    
    public static String getQueryUsername(Request request) {
        return request.queryParams("email");
    }
    
    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }
    
    public static String getQueryLoginRedirect(Request request) {
        return request.queryParams("loginRedirect");
    }
    
    public static String getQueryPasswordOld(Request request) {
        return request.queryParams("password_old");
    }
    
    public static String getQueryPasswordNew(Request request) {
        return request.queryParams("password_new");
    }
    
    public static String getSessionLocale(Request request) {
        return request.session().attribute("locale");
    }
    
    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }
    
    public static String getSessionCurrentUserRole(Request request) {return request.session().attribute("currentUserRole");}
    
    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }
    
    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }
}
