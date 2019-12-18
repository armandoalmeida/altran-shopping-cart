package com.altran.shoppingcart.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document
@Data
public class Item {
    @MongoId
    public ObjectId _id;

    private String id;
    @NotEmpty
    private String name;
    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    private Double value;

    private String user;
}
