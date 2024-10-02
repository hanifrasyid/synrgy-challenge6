package com.aplikasi.binarfudv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne                          // satu order bisa punya banyak order detail
    @JoinColumn(name = "order_id")      // order_id sebagai foreign key dari entitas order
    private Order order;

    @ManyToOne                          // satu product bisa punya banyak order detail
    @JoinColumn(name = "product_id")    // product_id sebagai foreign key dari entitas product
    private Product product;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_price")
    private Long total_price;
}