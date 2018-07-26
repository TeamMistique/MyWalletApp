package com.mistique.mywalletapp.mywalletapp.services;

import com.mistique.mywalletapp.mywalletapp.data.base.GenericRepository;
import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private GenericRepository<Category> repository;

    public CategoryServiceImp(GenericRepository<Category> repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAll() {
        return repository.listAll();
    }

    @Override
    public Category getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void update(int id, String name) {
        Category category = repository.findById(id);
        category.setName(name);
        repository.update(id, category);
    }

    @Override
    public void update(int id, Type type) {
        Category category = repository.findById(id);
        category.setType(type);
        repository.update(id, category);
    }

    @Override
    public void create(String name, Type type) {
        repository.create(new Category(name, type));
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }
}
