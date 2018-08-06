package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mywallet/wallets")
public class WalletController {
    private WalletService service;

    @Autowired
    public WalletController(WalletService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public List<Wallet> getAll(){
        return service.listAll();
    }

    @RequestMapping("/get")
    public Wallet getById(@RequestParam int id){
        return service.getById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addWallet(@RequestParam String name){
        service.create(name, 0);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateWallet(@RequestParam int id, @RequestParam String name, @RequestParam double balance){
        service.update(id, name, balance);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteWallet(@RequestParam int id){
        service.delete(id);
    }

    @ExceptionHandler
    ResponseEntity<CustomError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Unable to parse something."), HttpStatus.BAD_REQUEST);
    }
}
