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

    @RequestMapping("/{id}")
    public Wallet getById(@PathVariable("id") String id){
        return service.getById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addWallet(@RequestParam("name") String name){
        service.create(name, 0);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void updateWallet(@PathVariable("id") String id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "balance", required = false) String balance){
        int idInt = Integer.parseInt(id);
        if(name!=null){
            service.update(idInt, name);
        }
        if(balance!=null){
            double doubleBalance = Double.parseDouble(balance);
            service.update(idInt, doubleBalance);
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deleteWallet(@PathVariable("id") String id){
        service.delete(Integer.parseInt(id));
    }

    @ExceptionHandler
    ResponseEntity<CustomError> handleException(NumberFormatException e) {
        return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(), "Unable to parse something."), HttpStatus.BAD_REQUEST);
    }
}
