package com.altran.shoppingcart.model;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public @Data
class Cart {
    @Id
    public ObjectId _id;
    private User user;
    private Map<Item, Integer> items;
    private CartStatusEnum status;
}
