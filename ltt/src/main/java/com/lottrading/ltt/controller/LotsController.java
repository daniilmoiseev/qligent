package com.lottrading.ltt.controller;

import com.lottrading.ltt.exception.MyIOException;
import com.lottrading.ltt.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("lots")
public class LotsController {

//    @Autowired
//    private LotRepository lotRepository;

    private int counter = 3;
    private List<Map<String, String>> bids = new ArrayList<>();
    private List<Map<String, String>> lots = new ArrayList<>(){{
        add(new HashMap<String, String>(){{ put("id", "1"); put("title", "Car"); put("buyout", "1000"); put("minBid", "500"); put("maxBid", "0"); }});
        add(new HashMap<String, String>(){{ put("id", "2"); put("title", "Toy"); put("buyout", "500"); put("minbid", "100"); put("maxBid", "0"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return lots;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getLot(id);
    }

    public Map<String, String> getLot(@PathVariable String id){
        return lots.stream()
                .filter(lot -> lot.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(//@RequestBody Map<String, String> lot
                                      @RequestParam String title,
                                      @RequestParam int buyoutPrice,
                                      @RequestParam int minBidPrice) {
        Map<String, String> lot = new HashMap<>(){{
            put("title", title);
            put("buyout", String.valueOf(buyoutPrice));
            put("minBid", String.valueOf(minBidPrice));
            put("id", String.valueOf(counter++));
            put("maxBid", "0");
        }};
//        lot.put("id", String.valueOf(counter++));

        lots.add(lot);
        return lot;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id,
                                      // @RequestBody Map<String, String> lot
                                      @RequestParam int maxBidPrice) {
        Map<String, String> lotFromDb = getLot(id);
//        lotFromDb.putAll(lot);
//        lotFromDb.put("id", id);
        if(Integer.parseInt(lotFromDb.get("maxBid")) <= maxBidPrice && Integer.parseInt(lotFromDb.get("minBid")) <= maxBidPrice)
            lotFromDb.put("maxBid", String.valueOf(maxBidPrice));
        else throw new MyIOException();
        return lotFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> lot = getLot(id);
        lots.remove(lot);
    }
}

/*
@RequestBody Map<String, String> lot
@RequestParam String title, @RequestParam int buyoutPrice, @RequestParam int minBidPrice
 */