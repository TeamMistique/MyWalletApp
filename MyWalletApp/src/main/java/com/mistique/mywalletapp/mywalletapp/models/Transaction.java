package com.mistique.mywalletapp.mywalletapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int id;

    @Column(name = "Amount")
    private double amount;

    @Column(name = "Time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "WalletID")
    @JsonManagedReference
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    @JsonManagedReference
    private Category category;

    @Column(name = "Notes")
    private String notes;

    public Transaction() {
    }

    public Transaction(double amount, Date time, Wallet wallet, Category category, String notes) {
        this.amount = amount;
        this.time = time;
        this.wallet = wallet;
        this.category = category;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
