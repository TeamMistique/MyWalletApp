package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.data.TypeSqlRepository;
import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private GenericRepository<Type> repository;

    @Autowired
    public TypeServiceImpl(GenericRepository<Type> repository) {
        this.repository = repository;
    }

    @Override
    public List<Type> getAll() {
        return repository.listAll();
    }

    @Override
    public Type getById(int id) {
        return repository.findById(id);
    }
}
