package com.mistique.mywalletapp.mywalletapp.services.base;

import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Type;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(int id);

    void update(int id, String name, Type type);

    void create(String name, Type type);

    void delete(int id);

}
