package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractHibernateDao;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;

public class WalletSqlDAO extends AbstractHibernateDao {
    public WalletSqlDAO() {
        setClazz(Wallet.class);
    }
}
