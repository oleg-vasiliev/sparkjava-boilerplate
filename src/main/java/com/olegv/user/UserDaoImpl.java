package com.olegv.user;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Optional;

@Singleton
public class UserDaoImpl implements UserDao {
    
    private final HashMap<String, User> users;
    
    // The password is "password" for all users
    public UserDaoImpl() {
        User root = new User("root-uid", "root@example.com", UserRole.ROOT, "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                             "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO");
        User user = new User("user-uid", "user@example.com", UserRole.USER, "$2a$10$E3DgchtVry3qlYlzJCsyxe",
                             "$2a$10$E3DgchtVry3qlYlzJCsyxeSK0fftK4v0ynetVCuDdxGVl1obL.ln2");
        
        users = new HashMap<>();
        users.put(root.getUid(), root);
        users.put(user.getUid(), user);
    }
    
    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(users.values().stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null));
    }
    
    @Override
    public Optional<User> getUserByUID(String uid) {
        return Optional.ofNullable(users.values().stream().filter(b -> b.getUid().equals(uid)).findFirst().orElse(null));
    }
    
    @Override
    public User saveUser(User user) {
        users.put(user.getUid(), user);
        return user;
    }
    
}
