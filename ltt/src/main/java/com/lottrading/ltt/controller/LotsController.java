package com.lottrading.ltt.controller;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
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
    public List<LotDto> findAllLots(){
        return lotService.findAll();
    }

    @GetMapping("lots/{id}")
    public LotDto getOneLot(@PathVariable Long id){
        return lotService.getOneLot(id);
    }

    @PostMapping("lots")
    public LotDto createLot(@RequestParam String title,
                         @RequestParam int buyout,
                         @RequestParam int minBid) {
        return lotService.createLot(title, buyout, minBid);
    }

    @PutMapping("lots")
    public LotDto offerBid(@RequestParam Long id,
                            @RequestParam Long userId,
                            @RequestParam int yourBid) {
        return lotService.offerBid(id, userId, yourBid);
    }

    @DeleteMapping("lots/{id}")
    public void deleteLot(@PathVariable Long id,
                          @RequestParam Long userId) {
        lotService.deleteLot(id, userId, getOneLot(id).getBuyout());
    }

    @GetMapping("users")
    public List<UserDto> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping("users/{id}")
    public UserDto getOneUser(@PathVariable Long id){
        return userService.getOneUser(id);
    }

    @PostMapping("users")
    public UserDto createUser(@RequestParam int cash) {
        return userService.createUser(cash);
    }
}