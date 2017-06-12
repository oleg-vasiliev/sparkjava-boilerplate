package com.olegv.user;

import com.olegv.util.Path;
import lombok.Getter;

public class UserRole {
    
    @Getter
    public static final String ROOT = "ROOT";
    @Getter
    public static final String USER = "USER";
    
    public static String getUserRoleHomepage(String role) {
        if (ROOT.equalsIgnoreCase(role))
            return Path.Web.CONTROL_PANEL_DASHBOARD;
        else if (USER.equalsIgnoreCase(role))
            return Path.Web.CONTROL_PANEL_DASHBOARD;
        else
            return Path.Web.LOGIN;
    }
}
