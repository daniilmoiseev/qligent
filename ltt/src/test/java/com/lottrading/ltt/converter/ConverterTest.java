package com.lottrading.ltt.converter;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.BuyoutDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.dto.UserDto;
import com.lottrading.ltt.models.Bid;
import com.lottrading.ltt.models.Buyout;
import com.lottrading.ltt.models.Lot;
import com.lottrading.ltt.models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void convertBidToDto() {
        BidToDtoConverter converter = new BidToDtoConverter();
        BidDto dto = converter.convert(new Bid(100));
        assertEquals(100, dto.getBid());
    }

    /*@Test
    void convertBidToEntity() {
        BidRepository repo = mock(BidRepository.class);
        BidToEntityConverter converter = new BidToEntityConverter(repo);

        when(repo.findById(any())).thenReturn(Optional.of(new Bid(22)));
        Bid entity = converter.convert(BidDto.builder().bid(22).build());

        assertEquals(22, entity.getBid());
    }*/

    @Test
    void convertBuyoutToDto() {
        BuyoutToDtoConverter converter = new BuyoutToDtoConverter();
        BuyoutDto dto = converter.convert(new Buyout(2000));
        assertEquals(2000, dto.getBuyout());
    }

    /*@Test
    void convertBuyoutToEntity() {
        BuyoutRepository repo = mock(BuyoutRepository.class);
        BuyoutToEntityConverter converter = new BuyoutToEntityConverter(repo);

        when(repo.findById(any())).thenReturn(Optional.of(new Buyout(2000)));
        Buyout entity = converter.convert(BuyoutDto.builder().buyout(2000).build());

        assertEquals(2000, entity.getBuyout());
    }*/

    @Test
    void convertLotToDto() {
        LotToDtoConverter converter = new LotToDtoConverter();
        Lot lot = new Lot("1", 100, 10, 60, false, new ArrayList<>());
        lot.setId(1);
        LotDto dto = converter.convert(lot);

        assertNotNull(dto);
        assertEquals(100, dto.getBuyout());
        assertFalse(dto.isArchive());
    }

    /*@Test
    void convertLotToEntity() {
        LotRepository repo = mock(LotRepository.class);
        LotToEntityConverter converter = new LotToEntityConverter(repo);

        Lot lot = new Lot("1", 100, 10, 60, false, new ArrayList<>());
        lot.setId(1);

        when(repo.findById(any())).thenReturn(Optional.of(lot));
        Lot entity = converter.convert(LotDto.builder().title("1").buyout(100).archive(false).build());

        assertNotNull(entity);
        assertEquals(100, entity.getBuyout());
        assertFalse(entity.isArchive());
    }*/

    @Test
    void convertUserToDto() {
        UserToDtoConverter converter = new UserToDtoConverter();
        User user = new User(500, new ArrayList<>(), new ArrayList<>());
        user.setId(1);
        UserDto dto = converter.convert(user);

        assertNotNull(dto);
        assertEquals(500, dto.getCash());
        assertEquals(new ArrayList<>(), dto.getBids());
    }

    /*@Test
    void convertUserToEntity() {
        UserRepository repo = mock(UserRepository.class);
        UserToEntityConverter converter = new UserToEntityConverter(repo);

        User user = new User(500, new ArrayList<>(), new ArrayList<>());
        user.setId(1);

        when(repo.findById(any())).thenReturn(Optional.of(user));
        User entity = converter.convert(UserDto.builder().cash(500).buyouts(new ArrayList<>()).build());

        assertNotNull(entity);
        assertEquals(500, entity.getCash());
        assertEquals(new ArrayList<>(), entity.getBuyouts());
    }*/
}