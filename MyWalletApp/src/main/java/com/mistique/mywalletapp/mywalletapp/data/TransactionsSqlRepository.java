package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractGenericRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
