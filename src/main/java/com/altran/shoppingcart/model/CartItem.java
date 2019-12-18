package com.altran.shoppingcart.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@Data
@Builder
public class CartItem {
    private Item item;
    private Integer quantity;

    public CartItem(Item item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
