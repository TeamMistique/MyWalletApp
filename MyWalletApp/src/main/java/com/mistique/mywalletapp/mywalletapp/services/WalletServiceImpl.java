package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    private GenericRepository<Wallet> repository;

    @Autowired
    public WalletServiceImpl(GenericRepository<Wallet> repository) {
        this.repository = repository;
    }

    @Override
    public void create(String name, double balance) {
        Wallet wallet = new Wallet(name, balance);
        repository.create(wallet);
    }

    @Override
    public List<Wallet> listAll() {
        return repository.listAll();
    }

    @Override
    public Wallet getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void update(int id, String name) {
        Wallet wallet = repository.findById(id);
        wallet.setName(name);
        repository.update(id, wallet);
    }

    @Override
    public void update(int id, double balance) {
        Wallet wallet = repository.findById(id);
        wallet.setBalance(balance);
        repository.update(id, wallet);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }
}
