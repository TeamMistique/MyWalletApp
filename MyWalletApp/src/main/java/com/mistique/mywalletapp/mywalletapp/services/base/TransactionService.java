package com.mistique.mywalletapp.mywalletapp.services.base;

import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void create(double amount, Date time, Wallet wallet, Category category, String notes);

    List<Transaction> getAll();

    List<Transaction> filter(Wallet wallet, Category category, Date fromDate, Date endDate);

    Transaction getById(int id);

    void update(int id, double amount, Date time, Wallet wallet, Category category, String notes);

    void delete(int id);
}
