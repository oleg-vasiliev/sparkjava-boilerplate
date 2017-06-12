package com.olegv.user;

import java.util.Optional;

public interface UserDao {
    
    Optional<User> getUserByUsername(String username);
    
    Optional<User> getUserByUID(String uid);
    
    User saveUser(User user);
    
}
