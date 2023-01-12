package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String note;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private int amount;
    @Column
    private String dateOfManufacture;
    @Column
    private String dateOfExpiration;
    @Column
    private String category;
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="storageId",referencedColumnName = "id")
    private Storage storage;

}