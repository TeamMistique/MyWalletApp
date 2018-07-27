package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Category;
import com.mistique.mywalletapp.mywalletapp.models.Transaction;
import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.CategoryService;
import com.mistique.mywalletapp.mywalletapp.services.base.TransactionService;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/mywallet/transactions")
public class TransactionController {
    private TransactionService service;
    private WalletService walletService;
    private CategoryService categoryService;

    @Autowired
    public TransactionController(TransactionService service, WalletService walletService, CategoryService categoryService) {
        this.service = service;
        this.walletService = walletService;
        this.categoryService = categoryService;
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
    public void addTransaction(@RequestParam double amount, @RequestParam String time, @RequestParam int walletID,
                               @RequestParam int categoryID, @RequestParam String notes) throws ParseException {
        Wallet wallet = walletService.getById(walletID);
        Category category = categoryService.getById(categoryID);
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        Date date = df.parse(time);
        service.create(amount, date, wallet, category, notes);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") String id, @RequestParam(required = false) double amount,
                       @RequestParam(required = false) Date time, @RequestParam(required = false) int walletID,
                       @RequestParam(required = false) int categoryID, @RequestParam(required = false) String notes) {
        int idInt = Integer.parseInt(id);
        if (amount != 0) {
            service.update(idInt, amount);
        }
        if (time != null) {
            service.update(idInt, time);
        }
        if (walletID != 0) {
            service.update(idInt, walletService.getById(walletID));
        }
        if (categoryID != 0) {
            service.update(idInt, categoryService.getById(categoryID));
        }
        if (notes != null) {
            service.update(idInt, notes);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        service.delete(Integer.parseInt(id));
    }

    @ExceptionHandler
    ResponseEntity<CustomError> handleException(Exception e) {
        return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Unable to parse something."), HttpStatus.BAD_REQUEST);
    }
}
