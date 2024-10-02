package com.aplikasi.binarfudv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
@Where(clause = "deleted_date is null")
public class Product extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private Long price;

    @ManyToOne                          // satu merchant bisa jual banyak produk
    @JoinColumn(name = "merchant_id")   // merchant_id sebagai foreign key dari entitas merchant
    private Merchant merchant;
}