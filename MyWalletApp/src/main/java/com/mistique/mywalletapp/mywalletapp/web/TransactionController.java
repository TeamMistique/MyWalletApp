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
@RequestMapping("/mywallet/transactions")
public class TransactionController {
    private TransactionService service;
    private WalletService walletService;
    private CategoryService categoryService;
    private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy KK:mm a", Locale.ENGLISH);

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
        Date date = df.parse(time);

        service.create(amount, date, wallet, category, notes);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") String id, @RequestParam Double amount,
                       @RequestParam String time, @RequestParam Integer walletID,
                       @RequestParam Integer categoryID, @RequestParam String notes) throws ParseException {
        int idInt = Integer.parseInt(id);
        Wallet wallet = walletService.getById(walletID);
        Category category = categoryService.getById(categoryID);
        Date date = df.parse(time);

        service.update(idInt, amount, date, wallet, category, notes);
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
