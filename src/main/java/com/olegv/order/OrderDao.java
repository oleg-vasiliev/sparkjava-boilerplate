package com.olegv.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    
    Optional<Order> getByUID(String uid);
    
    List<Order> getAll();
}
