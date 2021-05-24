package com.lottrading.ltt.controller;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.exception.MyIOException;
import com.lottrading.ltt.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class LotsController {

    private long counterLot = 1;
    private long counterUser = 1;
    private int time = 30;
    private List<LotDto> lots = new ArrayList<>();
    private List<UserDto> users = new ArrayList<>();

    @GetMapping("lots")
    public List<LotDto> list(){
        return lots;
    }

    @GetMapping("lots/{id}")
    public LotDto getOneLot(@PathVariable String id){
        return getLot(id);
    }

    public LotDto getLot(@PathVariable String id){
        return lots.stream()
                .filter(lot -> Long.toString(lot.getId()).equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public UserDto getUser(@PathVariable String id){
        return users.stream()
                .filter(user -> Long.toString(user.getId()).equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("lots")
    public LotDto createLot(@RequestParam String title,
                         @RequestParam int buyout,
                         @RequestParam int minBid) {
        LotDto lotDto = new LotDto(){{
            setId(counterLot++);
            setTitle(title);
            setBuyoutPrice(buyout);
            setMinBidPrice(minBid);
            setBuyoutTime(time);
            setBidPrices(new ArrayList<>(){{
                add(new HashMap<>(){{
                    put("userId", "none");
                    put("bid", "0");
                }});
            }});
        }};
        lots.add(lotDto);
        return lotDto;
    }

    @PutMapping("lots")
    public LotDto updateLot(@RequestParam String id,
                            @RequestParam String userId,
                            @RequestParam int yourBid) {
        LotDto lotFromDb = getLot(id);
        List<Map<String, String>> bids = lotFromDb.getBidPrices();
        Map<String, String> lastBids = bids.get(bids.size()-1);

        UserDto userFromDb = getUser(userId);
        List<Map<String, String>> userBids = userFromDb.getLotBids();
        Map<String, String> userLastBids = userBids.get(userBids.size()-1);

        if(Integer.parseInt(lastBids.get("bid")) <= yourBid && lotFromDb.getMinBidPrice() <= yourBid && !lastBids.get("userId").equals(userId)) {
            if(lastBids.get("userId").equals("none")) bids.remove(0);
            bids.add(new HashMap<>(){{
                put("userId", userId);
                put("bid", String.valueOf(yourBid));
            }});
            lotFromDb.setBidPrices(bids);

            if(userLastBids.get("lotId").equals("none")) userBids.remove(0);
            userBids.add(new HashMap<>(){{
                put("lotId", id);
                put("bid", String.valueOf(yourBid));
            }});
            userFromDb.setLotBids(userBids);
        }
        else throw new MyIOException();
        return lotFromDb;
    }

    @DeleteMapping("lots/{id}")
    public void deleteLot(@PathVariable String id,
                          @RequestParam String userId) {
        LotDto lot = getLot(id);
        UserDto user = getUser(userId);
        List<Map<String, String>> userBuyLots = user.getBuyLots();
        if(userBuyLots.get(0).get("lotId").equals("none")) {
            userBuyLots.remove(0);
        }
        userBuyLots.add(new HashMap<>(){{
            put("lotId", id);
            put("buyout", Integer.toString(lot.getBuyoutPrice()));
        }});
        user.setBuyLots(userBuyLots);
        lots.remove(lot);
    }

    @GetMapping("users")
    public List<UserDto> listUser(){
        return users;
    }

    @GetMapping("users/{id}")
    public UserDto getOneUser(@PathVariable String id){
        return getUser(id);
    }

    @PostMapping("users")
    public UserDto createUser() {
        UserDto userDto = new UserDto(){{
            setId(counterUser++);
            setLotBids(new ArrayList<>(){{
                add(new HashMap<>(){{
                    put("lotId", "none");
                    put("bid", "0");
                }});
            }});
            setBuyLots(new ArrayList<>(){{
                add(new HashMap<>(){{
                    put("lotId", "none");
                    put("buyout", "0");
                }});
            }});
        }};
        users.add(userDto);
        return userDto;
    }
}