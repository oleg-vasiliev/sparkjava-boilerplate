package com.olegv.user;

import java.util.Optional;

public interface UserService {
    
    boolean authenticate(String username, String password);
    
    Optional<User> authenticateAndGet(String username, String password);
    
    boolean setPassword(String username, String oldPassword, String newPassword);
}
