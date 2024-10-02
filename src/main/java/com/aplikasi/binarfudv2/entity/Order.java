package com.aplikasi.binarfudv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_time")
    private Date order_time;

    @Column(name = "destination_address")
    private String destination_address;

    @ManyToOne                          // satu customer bisa beli banyak order
    @JoinColumn(name = "customer_id")   // customer_id sebagai foreign key dari entitas customer
    private Customer customer;

    @Column(name = "completed")
    private Boolean completed;
}