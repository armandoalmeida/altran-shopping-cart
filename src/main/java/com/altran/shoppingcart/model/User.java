package com.altran.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @MongoId
    public ObjectId _id;

    private String id;
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
