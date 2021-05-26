package com.lottrading.ltt.controller;

import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.exception.MyIOException;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.LotBid;
import com.lottrading.ltt.models.UserBid;
import com.lottrading.ltt.models.UserBuyout;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<LotDto> lll = new ArrayList<>();
        lots.forEach(lot -> {
            if(!lot.isArchive()) lll.add(lot);
        });
        return lll;
    }

    @GetMapping("lots/{id}")
    public LotDto getOneLot(@PathVariable Long id){
        return getLot(id);
    }

    public LotDto getLot(@PathVariable Long id){
        return lots.stream()
                .filter(lot -> lot.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public UserDto getUser(@PathVariable Long id){
        return users.stream()
                .filter(user -> user.getId() == id)
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
            setBuyout(buyout);
            setMinBid(minBid);
            setBuyoutTime(time);
            setArchive(false);
            setLotBids(new ArrayList<>());
        }};
        lots.add(lotDto);
        return lotDto;
    }

    @PutMapping("lots")
    public LotDto updateLot(@RequestParam Long id,
                            @RequestParam Long userId,
                            @RequestParam int yourBid) {
        LotDto lotFromDb = getLot(id);
        List<LotBid> bids = lotFromDb.getLotBids();

        UserDto userFromDb = getUser(userId);
        List<UserBid> userBids = userFromDb.getUserBids();

        if(bids.isEmpty()){
            bids.add(new LotBid(){{
                setUserId(userId);
                setBid(yourBid);
            }});
            lotFromDb.setLotBids(bids);

            userBids.add(new UserBid(){{
                setLotId(id);
                setBid(yourBid);
            }});
            userFromDb.setUserBids(userBids);
        } else {
            LotBid lastBid = bids.get(bids.size()-1);
            if(lastBid.getBid() <= yourBid && lotFromDb.getMinBid() <= yourBid && lastBid.getUserId() != userId) {
                bids.add(new LotBid(){{
                    setUserId(userId);
                    setBid(yourBid);
                }});
                lotFromDb.setLotBids(bids);

                userBids.add(new UserBid(){{
                    setLotId(id);
                    setBid(yourBid);
                }});
                userFromDb.setUserBids(userBids);
            }
            else throw new MyIOException();
        }

        return lotFromDb;
    }

    @DeleteMapping("lots/{id}")
    public void deleteLot(@PathVariable Long id,
                          @RequestParam Long userId) {
        LotDto lot = getLot(id);
        if(!lot.isArchive()) {
            UserDto user = getUser(userId);
            List<UserBuyout> userBuyLots = user.getUserBuyouts();
            userBuyLots.add(new UserBuyout(){{
                setLotId(id);
                setBuyout(lot.getBuyout());
            }});
            user.setUserBuyouts(userBuyLots);
            lot.setArchive(true);
        }
//        lots.remove(lot);
    }

    @GetMapping("users")
    public List<UserDto> listUser(){
        return users;
    }

    @GetMapping("users/{id}")
    public UserDto getOneUser(@PathVariable Long id){
        return getUser(id);
    }

    @PostMapping("users")
    public UserDto createUser() {
        UserDto userDto = new UserDto(){{
            setId(counterUser++);
            setUserBids(new ArrayList<>());
            setUserBuyouts(new ArrayList<>());
        }};
        users.add(userDto);
        return userDto;
    }
}