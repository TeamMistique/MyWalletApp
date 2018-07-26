package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractGenericRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;

import java.util.List;

public class TransactionsSqlRepository extends AbstractGenericRepository<Transaction> implements GenericRepository<Transaction> {

    @Override
    public List<Transaction> listAll() {
        return null;
    }

    @Override
    public Transaction findById(int id) {
        return null;
    }

    @Override
    public void update(int id, Transaction entity) {

    }

    @Override
    public void delete(int id) {

    }
}
