package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ExceptionHandler
    ResponseEntity<WalletsError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new WalletsError(HttpStatus.BAD_REQUEST.value(), "Unable to parse employee id."), HttpStatus.BAD_REQUEST);
    }
}
