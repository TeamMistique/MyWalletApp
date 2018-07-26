package com.mistique.mywalletapp.mywalletapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;


@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int id;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "TypeID")
    @JsonManagedReference
    private Type type;

    public Category() {
    }

    public Category(String name, Type type) {
        this.name = name;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
