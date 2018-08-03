package com.mistique.mywalletapp.mywalletapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WalletID")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Balance")
    private double balance;

    @OneToMany(mappedBy = "wallet")
    @JsonBackReference
    private List<Transaction> transactions;

    public Wallet() {
    }

    public Wallet(String name, double balance) {
        this.name = name;
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int compareTo(Object o) {
        Wallet w = (Wallet) o;
        return Integer.compare(this.getId(), w.getId());
    }
}
