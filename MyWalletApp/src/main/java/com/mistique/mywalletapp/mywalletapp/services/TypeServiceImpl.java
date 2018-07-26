package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.data.TypeSqlRepository;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.TypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeSqlRepository repository;


    @Override
    public List<Type> getAll() {
        return repository.listAll();
    }

    @Override
    public Type getById(int id) {
        return repository.findById(id);
    }
}
