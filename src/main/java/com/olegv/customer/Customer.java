package com.olegv.customer;


import lombok.Value;

@Value
public class Customer {
    private String uid;
    private String name;
    private String address;
    private String photo;
    private String email;
    private String phone;
}


