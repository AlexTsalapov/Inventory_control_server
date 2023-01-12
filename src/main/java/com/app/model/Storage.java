package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="storage")
public class Storage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private boolean type;
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="userId",referencedColumnName = "id")
    private User user;
   @OneToMany(mappedBy = "storage",fetch = FetchType.EAGER,targetEntity = Product.class)
   @JsonIgnore
    private List<Product> products;


}