package com.mistique.mywalletapp.mywalletapp.services.base;

import com.mistique.mywalletapp.mywalletapp.models.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAll();

    Type getById(int id);
}
