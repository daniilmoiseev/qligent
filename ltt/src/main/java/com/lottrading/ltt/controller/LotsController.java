package com.lottrading.ltt.controller;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.exception.MyIOException;
import com.lottrading.ltt.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("lots")
public class LotsController {

    private long counter = 1;
    private List<LotDto> lots = new ArrayList<>();

    @GetMapping
    public List<LotDto> list(){
        return lots;
    }

    @GetMapping("{id}")
    public LotDto getOne(@PathVariable String id){
        return getLot(id);
    }

    public LotDto getLot(@PathVariable String id){
        return lots.stream()
                .filter(lot -> Long.toString(lot.getId()).equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public LotDto create(@RequestParam String title,
                         @RequestParam int buyout,
                         @RequestParam int minBid) {
        LotDto lotDto = new LotDto(){{
            setId(counter++);
            setTitle(title);
            setBuyoutPrice(buyout);
            setMinBidPrice(minBid);
            setBidPrices(new ArrayList<>(){{ add(0); }});
        }};
        lots.add(lotDto);
        return lotDto;
    }

    @PutMapping("{id}")
    public LotDto update(@PathVariable String id,
                         @RequestParam int yourBid) {
        LotDto lotFromDb = getLot(id);
        List<Integer> bids = lotFromDb.getBidPrices();
        Integer lastBid = bids.get(bids.size()-1);
        if(lastBid <= yourBid && lotFromDb.getMinBidPrice() <= yourBid) {
            if(lastBid == 0) bids.remove(0);
            bids.add(yourBid);
            lotFromDb.setBidPrices(bids);
        }
        else throw new MyIOException();
        return lotFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        LotDto lot = getLot(id);
        lots.remove(lot);
    }
}

/*
@RequestBody Map<String, String> lot
@RequestParam String title, @RequestParam int buyoutPrice, @RequestParam int minBidPrice
 */