package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.TypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TypeServiceImpl implements TypeService {
    IGenericDao<Type> repository;

    @Autowired
    public void setRepository(IGenericDao<Type> repositoryToSet) {
        this.repository = repositoryToSet;
        repository.s
    }

    @Override
    public List<Type> getAll() {
        return null;
    }

    @Override
    public Type getById(int id) {
        return null;
    }
}
