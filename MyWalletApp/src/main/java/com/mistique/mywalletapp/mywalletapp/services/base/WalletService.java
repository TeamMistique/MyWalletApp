package com.mistique.mywalletapp.mywalletapp.services.base;

import com.mistique.mywalletapp.mywalletapp.models.Wallet;

import java.util.List;

public interface WalletService {
    void create(String name, double balance);

    List<Wallet> listAll();

    Wallet getById(int id);

    void update(int id, String name);

    void update(int id, double balance);

    void delete(int id);
}
