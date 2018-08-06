package com.mistique.mywalletapp.mywalletapp.web;

import com.mistique.mywalletapp.mywalletapp.models.Wallet;
import com.mistique.mywalletapp.mywalletapp.services.base.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public Wallet addWallet(@RequestParam String name, @RequestParam double balance){
        service.create(name, balance);

        List<Wallet> wl = service.listAll().stream().filter(x-> x.getName().equals(name)).collect(Collectors.toList());
        return wl.get(wl.size()-1);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Wallet updateWallet(@RequestParam int id, @RequestParam String name, @RequestParam double balance){
        service.update(id, name, balance);

        return service.getById(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Wallet deleteWallet(@RequestParam int id){
        Wallet wallet = service.getById(id);
        service.delete(id);
        System.out.println(wallet);
        return wallet;
    }

    @ExceptionHandler
    ResponseEntity<CustomError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Unable to parse something."), HttpStatus.BAD_REQUEST);
    }
}
