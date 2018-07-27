package com.mistique.mywalletapp.mywalletapp.web;


import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.CategoryService;
import com.mistique.mywalletapp.mywalletapp.services.base.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category getById(@PathVariable("id") String id) {
        return service.getById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void updateCategory(@PathVariable("id") String idString, @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "type", required = false) Integer typeId) {
        int id = Integer.parseInt(idString);
        if (name != null) {
            service.update(id, name);
        }

        if (typeId != null) {
            Type type = typeService.getById(typeId);
            service.update(id, type);
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("id") String id) {
        service.delete(Integer.parseInt(id));
    }


    @ExceptionHandler
    ResponseEntity<CustomError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Unable to parse something."), HttpStatus.BAD_REQUEST);
    }

}
