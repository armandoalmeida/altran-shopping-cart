package com.altran.shoppingcart.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public @Data
class Item {
    @Id
    public ObjectId _id;
    private String name;
    private Double value;
}
