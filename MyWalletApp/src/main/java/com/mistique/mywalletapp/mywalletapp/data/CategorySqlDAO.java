package com.mistique.mywalletapp.mywalletapp.data;

import com.mistique.mywalletapp.mywalletapp.data.base.AbstractHibernateDao;
import com.mistique.mywalletapp.mywalletapp.models.Category;

public class CategorySqlDAO extends AbstractHibernateDao {
    public CategorySqlDAO() {
        setClazz(Category.class);
    }
}
