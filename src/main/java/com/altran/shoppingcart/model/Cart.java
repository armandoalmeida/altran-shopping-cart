package com.altran.shoppingcart.model;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document
public @Data
class Cart {
    @Id
    public ObjectId _id;
    public String id;
    private List<Item> items;
    private CartStatusEnum status;
    private BigDecimal total;

    private String user;
}
