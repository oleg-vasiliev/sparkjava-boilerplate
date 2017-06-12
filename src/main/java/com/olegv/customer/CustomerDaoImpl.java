package com.olegv.customer;

import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class CustomerDaoImpl implements CustomerDao {
    
    private final List<Customer> customers = ImmutableList.of(
            new Customer("uid1", "Walter Schultz", "1472 Pockrus Page Rd", "https://randomuser.me/api/portraits/men/89.jpg", "wschultz@mail.com", "202-555-0173"),
            new Customer("uid2", "Zachary Wade", "5998 White Oak Dr ", "https://randomuser.me/api/portraits/men/86.jpg", "zwade@mail.com", "512-555-0171"),
            new Customer("uid3", "Diana Wood", "6306 Brentwood Dr", "https://randomuser.me/api/portraits/women/10.jpg", "dwood@mail.com", "208-555-0120"),
            new Customer("uid4", "Jason Snyder", "7567 Oak Ridge Ln", "https://randomuser.me/api/portraits/men/79.jpg", "jsnyder@mail.com", "803-555-0142"),
            new Customer("uid5", "Carl Tran", "2152 Abby Park St", "https://randomuser.me/api/portraits/men/42.jpg", "ctran@mail.com", "515-555-0143")
    );
    
    @Override
    public Optional<Customer> getByUID(String uid) {
        return Optional.ofNullable(customers.stream().filter(b -> b.getUid().equalsIgnoreCase(uid)).findFirst().orElse(null));
    }
    
    @Override
    public List<Customer> getAll() {
        return customers;
    }
    
}
