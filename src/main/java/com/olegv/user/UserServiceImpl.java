package com.olegv.user;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {
    
    @Inject
    private UserDao userDao;
    
    @Override
    public boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        Optional<User> user = userDao.getUserByUsername(username);
        if (!user.isPresent()) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.get().getSalt());
        return hashedPassword.equals(user.get().getHashedPassword());
    }
    
    @Override
    public Optional<User> authenticateAndGet(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return Optional.empty();
        }
        Optional<User> user = userDao.getUserByUsername(username);
        if (user.isPresent()) {
            String hashedPassword = BCrypt.hashpw(password, user.get().getSalt());
            return hashedPassword.equals(user.get().getHashedPassword()) ? user : Optional.empty();
        } else
            return Optional.empty();
    }
    
    @Override
    public boolean setPassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOptional = authenticateAndGet(username, oldPassword);
        if (userOptional.isPresent()) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newPassword, newSalt);
            User user = userOptional.get();
            user.setSalt(newSalt);
            user.setHashedPassword(newHashedPassword);
            userDao.saveUser(user);
            return true;
        } else {
            return false;
        }
    }
}
