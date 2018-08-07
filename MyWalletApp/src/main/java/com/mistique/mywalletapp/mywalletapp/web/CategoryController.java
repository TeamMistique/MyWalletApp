package com.mistique.mywalletapp.mywalletapp.web;


import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.CategoryService;
import com.mistique.mywalletapp.mywalletapp.services.base.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mywallet/categories")
public class CategoryController {
    private CategoryService service;
    private TypeService typeService;

    public CategoryController(CategoryService service, TypeService typeService) {
        this.service = service;
        this.typeService = typeService;
    }

    @RequestMapping("/")
    public List<Category> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Category create(@RequestParam String name, @RequestParam Integer typeId) {
        Type type  = typeService.getById(typeId);
        service.create(name, type);
        List<Category> categories = service.getAll();
        return categories.get(categories.size()-1);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Category getById(@RequestParam int id) {
        return service.getById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Category updateCategory(@RequestParam int id , @RequestParam String name,
                               @RequestParam int typeId) {
        Type type = typeService.getById(typeId);
        service.update(id, name, type);
        return service.getById(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Category deleteCategory(@RequestParam int id) {
        Category category = service.getById(id);
        service.delete(id);
        return category;
    }


    @ExceptionHandler
    ResponseEntity<CustomError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Unable to parse something."), HttpStatus.BAD_REQUEST);
    }

}
