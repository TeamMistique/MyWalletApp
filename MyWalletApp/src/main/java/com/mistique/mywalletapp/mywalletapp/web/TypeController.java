package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Type;
import com.mistique.mywalletapp.mywalletapp.services.base.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {
    private TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @RequestMapping("/")
    public List<Type> getAll() {
        return typeService.getAll();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public Type getById(@PathVariable("id") String id) {
        return typeService.getById(Integer.parseInt(id));
    }
}
