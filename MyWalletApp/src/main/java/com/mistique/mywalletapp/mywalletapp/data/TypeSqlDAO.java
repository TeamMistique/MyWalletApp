package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractHibernateDao;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import org.springframework.stereotype.Repository;

@Repository
public class TypeSqlDAO extends AbstractHibernateDao {
    public TypeSqlDAO() {
        setClazz(Type.class);
    }
}
