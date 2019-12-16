package com.altran.shoppingcart.model;

import com.altran.shoppingcart.enumeration.CartStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    public ObjectId _id;
    public String id;
    private List<CartItem> items;
    private CartStatusEnum status;
    private BigDecimal total;

    private String user;

    public Cart(List<CartItem> items) {
        this.items = items;
    }
}
