package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.TransactionService;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private GenericRepository<Transaction> repository;
    private WalletService walletService;

    @Autowired
    public TransactionServiceImpl(GenericRepository<Transaction> repository, WalletService walletService) {
        this.repository = repository;
        this.walletService = walletService;
    }

    @Override
    public void create(double amount, Date time, Wallet wallet, Category category, String notes) {
        if(category.getType().getId()==2){
            amount = -amount;
        }
        Transaction transaction = new Transaction(amount, time, wallet, category, notes);
        repository.create(transaction);
        walletService.update(wallet.getId(), wallet.getBalance()+transaction.getAmount());
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
        Wallet wallet = transaction.getWallet();
        double newBalance = wallet.getBalance()-transaction.getAmount();
        walletService.update(wallet.getId(), newBalance);
        if(transaction.getCategory().getType().getId()==2){
            amount = -amount;
        }
        transaction.setAmount(amount);
        newBalance = newBalance+amount;
        repository.update(id, transaction);
        walletService.update(wallet.getId(), newBalance);
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
        Wallet oldWallet = transaction.getWallet();
        walletService.update(oldWallet.getId(), oldWallet.getBalance()-transaction.getAmount());
        walletService.update(wallet.getId(), wallet.getBalance()+transaction.getAmount());
        transaction.setWallet(wallet);
        repository.update(id, transaction);
    }

    @Override
    public void update(int id, Category category) {
        Transaction transaction = repository.findById(id);
        Category oldCategory = transaction.getCategory();
        transaction.setCategory(category);

        if(oldCategory.getType().getId()!=category.getType().getId()){
            transaction.setAmount(-transaction.getAmount());
            Wallet wallet = transaction.getWallet();
            walletService.update(wallet.getId(), wallet.getBalance()+2*transaction.getAmount());
        }

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
        Transaction transaction = repository.findById(id);
        Wallet wallet = transaction.getWallet();
        walletService.update(wallet.getId(), wallet.getBalance()-transaction.getAmount());

        repository.delete(id);
    }
}
