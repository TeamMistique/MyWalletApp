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
import java.util.stream.Collectors;

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
        amount = Math.abs(amount);
        if(category.getType().getId()==2){
            amount = -amount;
        }

        Transaction transaction = new Transaction(amount, time, wallet, category, notes);
        repository.create(transaction);
        walletService.update(wallet.getId(), wallet.getName(), wallet.getBalance()+transaction.getAmount());
    }

    @Override
    public List<Transaction> getAll() {
        return repository.listAll();
    }

    @Override
    public List<Transaction> filter(Wallet wallet, Category category, Date fromDate, Date endDate) {
        List<Transaction> result = repository.listAll();

        if(wallet!=null){
            result = result.stream().filter(x -> x.getWallet().getId()==wallet.getId()).collect(Collectors.toList());
        }

        if(category!=null){
            result = result.stream().filter(x -> x.getCategory().getId()==category.getId()).collect(Collectors.toList());
        }

        if(fromDate!=null){
            result = result.stream().filter(x -> x.getTime().compareTo(fromDate)>=0).collect(Collectors.toList());
        }

        if(endDate!=null){
            result = result.stream().filter(x -> x.getTime().compareTo(endDate)<=0).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public Transaction getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void update(int id, double amount, Date time, Wallet wallet, Category category, String notes) {
        amount = Math.abs(amount);
        if(category.getType().getId()==2){
            amount = -amount;
        }

        Transaction updatedTransaction = new Transaction(amount, time, wallet, category, notes);
        Transaction originalTransaction = repository.findById(id);

        //Set the original transaction's wallet's balance to the way it was prior to that transaction.
        Wallet oldWallet = originalTransaction.getWallet();
        double newBalance = oldWallet.getBalance()-originalTransaction.getAmount();
        walletService.update(oldWallet.getId(), oldWallet.getName(), newBalance);

        //Set the new wallet's balance.
        Wallet newWallet = updatedTransaction.getWallet();
        newBalance = newWallet.getBalance()+updatedTransaction.getAmount();
        walletService.update(newWallet.getId(), newWallet.getName(), newBalance);

        //Update the transaction.
        repository.update(id, updatedTransaction);
    }

    @Override
    public void delete(int id) {
        Transaction transaction = repository.findById(id);
        Wallet wallet = transaction.getWallet();
        walletService.update(wallet.getId(), wallet.getName(), wallet.getBalance()-transaction.getAmount());

        repository.delete(id);
    }
}
