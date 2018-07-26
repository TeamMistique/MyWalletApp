package com.mistique.mywalletapp.mywalletapp.models;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeID")
    private int id;

    @Column(name = "Name")
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
