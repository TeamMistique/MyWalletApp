package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
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

    @RequestMapping("/{id}")
    public Wallet getById(@PathVariable("id") String id){
        return service.getById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addWallet(@RequestParam("name") String name){
        service.create(name, 0);
    }

    @ExceptionHandler
    ResponseEntity<WalletsError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new WalletsError(HttpStatus.BAD_REQUEST.value(), "Unable to parse employee id."), HttpStatus.BAD_REQUEST);
    }
}
