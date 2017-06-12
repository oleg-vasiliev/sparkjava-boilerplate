package com.olegv;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.olegv.customer.CustomerDao;
import com.olegv.customer.CustomerDaoImpl;
import com.olegv.user.UserDao;
import com.olegv.user.UserDaoImpl;
import com.olegv.user.UserService;
import com.olegv.user.UserServiceImpl;
import com.olegv.util.CmdArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig implements Module {
    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
    
    public AppConfig(CmdArgs cmdArgs) {
        // Extract some configuration variables (using CmdArgs object or static AppProperties util)
        
    }
    
    @Override
    public void configure(Binder binder) {
        // Bind service implementations that does not require custom configuration
        binder.bind(CustomerDao.class).to(CustomerDaoImpl.class);
        binder.bind(UserService.class).to(UserServiceImpl.class);
    }
    
    // Through provider methods we can bind service implementations that requires custom configuration
    @Provides
    public UserDao provideUserDao() {
        return new UserDaoImpl();
    }
    
}
