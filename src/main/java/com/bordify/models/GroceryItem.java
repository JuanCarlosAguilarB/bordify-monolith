package com.bordify.models;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("groceryitems")
@Data
public class GroceryItem {

   @Id

    private UUID id;

    private String name;
    private int quantity;
    private String category;

    public GroceryItem(UUID id, String name, int quantity, String category) {
        super();
//        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }
}