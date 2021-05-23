package com.lottrading.ltt.controller;

import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MainController {

    @Autowired
    private LotRepository lotRepository;

    @GetMapping
    public Iterable<Lot> home() {
        Iterable<Lot> lt = lotRepository.findAll();
        return lt;
    }

    @PostMapping
    public String buyout(@RequestParam long id, Model model) {
        lotRepository.deleteById(id);
        return "redirect:/";
    }
}