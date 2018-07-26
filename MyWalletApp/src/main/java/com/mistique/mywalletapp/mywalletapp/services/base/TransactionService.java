package com.mistique.mywalletapp.mywalletapp.services.base;

import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void create(double amount, Date time, Wallet wallet, Category category, String notes);

    List<Transaction> getAll();

    Transaction getById(int id);

    void update(int id, double amount);

    void update(int id, Date time);

    void update(int id, Wallet wallet);

    void update(int id, Category category);

    void update(int id, String notes);

    void delete(int id);
}
