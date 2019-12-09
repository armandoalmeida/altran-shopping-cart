package com.altran.shoppingcart.model;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Document
public @Data
class Cart {
    @Id
    public ObjectId _id;
    @NotEmpty
    private User user;
    private Map<Item, Integer> items;
    @NotEmpty
    private CartStatusEnum status;
}
