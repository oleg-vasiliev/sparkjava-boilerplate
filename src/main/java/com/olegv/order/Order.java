package com.olegv.order;

import lombok.Value;

import java.util.Date;
import java.util.Map;

@Value
public class Order {
    String uid;
    String customerUid;
    Map<String, Integer> items;
    Integer totalValue;
    Date created;
}
