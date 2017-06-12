package com.olegv.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String uid;
    String username;
    String role;
    String salt;
    String hashedPassword;
}
