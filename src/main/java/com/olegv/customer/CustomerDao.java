package com.olegv.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    
    Optional<Customer> getByUID(String uid);
    
    List<Customer> getAll();
}
