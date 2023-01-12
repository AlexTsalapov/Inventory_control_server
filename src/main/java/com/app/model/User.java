package com.app.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor


@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String login;
    @Column
    private String password;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,targetEntity = Storage.class)
    @JsonIgnore
    private List<Storage> storages = Collections.emptyList();
}





