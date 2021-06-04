package com.lottrading.ltt.controller;

import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import com.lottrading.ltt.service.LotService;
import com.lottrading.ltt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class LotsController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final LotService lotService;

    public LotsController(UserService userService, LotService lotService) {
        this.userService = userService;
        this.lotService = lotService;
    }

    @GetMapping("lots")
    public List<Lot> findAllLots(){
        return lotService.findAll();
    }

    @GetMapping("lots/{id}")
    public Lot getOneLot(@PathVariable Long id){
        return lotService.getOneLot(id);
    }

    @PostMapping("lots")
    public Lot createLot(@RequestParam String title,
                         @RequestParam int buyout,
                         @RequestParam int minBid) {
        return lotService.createLot(title, buyout, minBid);
    }

    @PutMapping("lots")
    public Lot updateLot(@RequestParam Long id,
                         @RequestParam Long userId,
                         @RequestParam int yourBid) {
        return lotService.updateLot(id, userId, yourBid);
    }

    @DeleteMapping("lots/{id}")
    public void deleteLot(@PathVariable Long id,
                          @RequestParam Long userId) {
        lotService.deleteLot(id, userId);
    }

    @GetMapping("users")
    public List<User> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping("users/{id}")
    public User getOneUser(@PathVariable Long id){
        return userService.getOneUser(id);
    }

    @PostMapping("users")
    public User createUser() {
        return userService.createUser();
    }
}