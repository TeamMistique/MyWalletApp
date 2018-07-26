package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractHibernateDao;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;

public class TransactionSqlDAO extends AbstractHibernateDao {
    public TransactionSqlDAO() {
        setClazz(Transaction.class);
    }
}
