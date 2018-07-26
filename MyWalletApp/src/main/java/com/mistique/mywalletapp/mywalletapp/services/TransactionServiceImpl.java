package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private GenericRepository<Transaction> repository;

    @Autowired
    public TransactionServiceImpl(GenericRepository<Transaction> repository) {
        this.repository = repository;
    }

    @Override
    public void create(double amount, Date time, Wallet wallet, Category category, String notes) {
        Transaction transaction = new Transaction(amount, time, wallet, category, notes);
        repository.create(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return repository.listAll();
    }

    @Override
    public Transaction getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void update(int id, double amount) {
        Transaction transaction = repository.findById(id);
        transaction.setAmount(amount);
        repository.update(id, transaction);
    }

    @Override
    public void update(int id, Date time) {
        Transaction transaction = repository.findById(id);
        transaction.setTime(time);
        repository.update(id, transaction);
    }

    @Override
    public void update(int id, Wallet wallet) {
        Transaction transaction = repository.findById(id);
        transaction.setWallet(wallet);
        repository.update(id, transaction);
    }

    @Override
    public void update(int id, Category category) {
        Transaction transaction = repository.findById(id);
        transaction.setCategory(category);
        repository.update(id, transaction);
    }

    @Override
    public void update(int id, String notes) {
        Transaction transaction = repository.findById(id);
        transaction.setNotes(notes);
        repository.update(id, transaction);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }
}
