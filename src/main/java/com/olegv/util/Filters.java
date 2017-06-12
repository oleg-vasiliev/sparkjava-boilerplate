package com.olegv.util;

import spark.Filter;
import spark.Request;
import spark.Response;

import static com.olegv.util.RequestUtil.getQueryLocale;

public class Filters {
    
    // If a users manually manipulates paths and forgets to add
    // a trailing slash, redirect the users to the correct path
    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };
    
    // Locale change can be initiated from any page
    // The locale is extracted from the request and saved to the users's session
    public static Filter handleLocaleChange = (Request request, Response response) -> {
        if (getQueryLocale(request) != null) {
            request.session().attribute("locale", getQueryLocale(request));
            response.redirect(request.pathInfo());
        }
    };
    
    // Enable GZIP for all responses
    public static Filter addGzipHeader = (Request request, Response response) -> {
        response.header("Content-Encoding", "gzip");
    };
    
}
