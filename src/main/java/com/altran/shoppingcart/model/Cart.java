package com.altran.shoppingcart.model;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public @Data
class Cart {
    @Id
    public ObjectId _id;
    public String id;
    private List<String> items;
    private CartStatusEnum status;

    private String user;
}
