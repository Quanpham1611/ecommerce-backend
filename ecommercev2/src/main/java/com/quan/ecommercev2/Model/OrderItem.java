package com.quan.ecommercev2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private double orderedProductPrice;

    public void setOrderedProductPrice(Product product){
        double price = product.getPrice() * this.getQuantity();
        this.orderedProductPrice = price;
    }

    public Double getOrderedProductPrice(){
        return orderedProductPrice;
    }
}
