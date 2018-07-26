package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/mywallet/transactions")
public class TransactionController {
    private TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public List<Transaction> getAll() {
        return service.getAll();
    }

    @RequestMapping("/{id}")
    public Transaction getById(@PathVariable String id) {
        return service.getById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addTransaction(double amount, Date time, Wallet wallet, Category category, String notes) {
        service.create(amount, time, wallet, category, notes);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(String id, double amount, Date time, Wallet wallet, Category category, String notes) {
        int idInt = Integer.parseInt(id);
        if (amount != 0) {
            service.update(idInt, amount);
        }
        if (time != null) {
            service.update(idInt, time);
        }
        if (wallet != null) {
            service.update(idInt, wallet);
        }
        if (category != null) {
            service.update(idInt, category);
        }
        if (notes != null) {
            service.update(idInt, notes);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        service.delete(Integer.parseInt(id));
    }
}
