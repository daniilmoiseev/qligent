package com.lottrading.ltt.dao;

import com.lottrading.ltt.converter.LotToDtoConverter;
import com.lottrading.ltt.converter.LotToEntityConverter;
import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.exception.MyIOException;
import com.lottrading.ltt.exception.NotFoundException;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class LotDao {
    @Autowired
    private final LotRepository lotRepository;

    @Autowired
    private final UserDao userDao;

    @Autowired
    private final BidDao bidDao;

    @Autowired
    private final BuyoutDao buyoutDao;

    @Autowired
    private final LotToDtoConverter lotToDtoConverter;

    @Autowired
    private final LotToEntityConverter lotToEntityConverter;

    public LotDao(LotRepository lotRepository, UserDao userDao, BidDao bidDao, BuyoutDao buyoutDao, LotToDtoConverter lotToDtoConverter, LotToEntityConverter lotToEntityConverter) {
        this.lotRepository = lotRepository;
        this.userDao = userDao;
        this.bidDao = bidDao;
        this.buyoutDao = buyoutDao;
        this.lotToDtoConverter = lotToDtoConverter;
        this.lotToEntityConverter = lotToEntityConverter;
    }

    public LotDto createLot(String title, int buyout, int minBid) {
        Lot lot = new Lot(title, buyout, minBid, 60, false, new ArrayList<>());
        lotRepository.saveAndFlush(lot);
        return lotToDtoConverter.convert(lot);
    }

    public LotDto deleteLot(Long lotId, Long userId, int buyout) {
        LotDto lotDto = getOneLot(lotId);
        UserDto userDto = userDao.getOneUser(userId);
        if(!lotDto.isArchive() && userDto.getCash() >= buyout) {
            userDto.setCash(userDto.getCash() - buyout);
            userDao.updateUser(userDto);
            buyoutDao.saveBuyout(lotDto, userDto, buyout);
            lotDto.setArchive(true);
            Lot lot = lotToEntityConverter.convert(lotDto);
            lotRepository.saveAndFlush(lot);
        } else throw new MyIOException();
        return getOneLot(lotId);
    }

    public LotDto offerBid(Long lotId, Long userId, int yBid) {
        LotDto lotDto = getOneLot(lotId);
        List<BidDto> bids = lotDto.getBids();

        UserDto userDto = userDao.getOneUser(userId);

        if(bids.isEmpty()){
            if(userDto.getCash() >= yBid && lotDto.getMinBid() <= yBid) {
                bidDao.saveBid(lotDto, userDto, yBid);
            }
            else throw new MyIOException();
        } else {
            BidDto lastBid = bids.get(bids.size()-1);
            if(lastBid.getBid() < yBid && lotDto.getMinBid() <= yBid && lastBid.getUserId() != userId && userDto.getCash() >= yBid && lotDto.getBuyoutTime() > 0) {
                bidDao.saveBid(lotDto, userDto, yBid);
            }
            else throw new MyIOException();
        }
        return getOneLot(lotId);
    }

    @Transactional(readOnly = true)
    public List<LotDto> findAll() {
        List<Lot> lots = lotRepository.findByArchiveIsFalse();
        List<LotDto> lotDtoList = new ArrayList<>();
        lots.forEach(it -> {
            LotDto lotDto = lotToDtoConverter.convert(it);
            lotDtoList.add(lotDto);
        });
        lotDtoList.forEach(it -> {
            List<BidDto> bids = bidDao.findByLotId(it.getId());
            if(!bids.isEmpty()){
                it.setBids(bids);
            } else {
                it.setBids(new ArrayList<>());
            }
        });

        return lotDtoList;
    }

    @Transactional(readOnly = true)
    public LotDto getOneLot(long id) {
        Lot lot = lotRepository.findById(id).orElseThrow(NotFoundException::new);
        LotDto lotDto = lotToDtoConverter.convert(lot);
        List<BidDto> bids = bidDao.findByLotId(lot.getId());
        if(!bids.isEmpty()){
            lotDto.setBids(bids);
        } else {
            lotDto.setBids(new ArrayList<>());
        }
        return lotDto;
    }
}
