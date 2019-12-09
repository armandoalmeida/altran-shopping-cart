package com.altran.shoppingcart.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Document
public @Data
class Item {
    @Id
    public ObjectId _id;
    @NotEmpty
    private String name;
    @NotEmpty
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal value;
}
