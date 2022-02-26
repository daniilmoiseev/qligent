package com.lottrading.ltt;

import com.lottrading.ltt.dto.BidDto;
import com.lottrading.ltt.dto.LotDto;
import com.lottrading.ltt.service.LotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class LotTradingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JacksonTester<LotDto> json;

	@MockBean
	private LotService lotService;

	@Test
	void findAll() throws Exception {
		when(lotService.findAll()).thenReturn(
				List.of(
						LotDto.builder()
								.id(1)
								.title("1")
								.archive(false)
								.buyoutTime(1)
								.buyout(100)
								.minBid(10)
								.bids(new ArrayList<>()).build()
						));
		this.mockMvc.perform(get("/lots"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("lots[0].archive").value(false))
				.andExpect(jsonPath("lots[0].bids", hasSize(0)));
	}

	@Test
	void getOneLot() throws Exception {
		when(lotService.getOneLot(anyLong())).thenReturn(
				LotDto.builder()
						.id(2)
						.title("2")
						.archive(true)
						.buyout(200)
						.buyoutTime(1)
						.minBid(50)
						.bids(List.of(BidDto.builder().bid(150).lotId(2).userId(1).id(1).build())).build()
				);
		this.mockMvc.perform(get("/lots/2"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("bids[0].bid").value(150))
				.andExpect(jsonPath("archive").value(true));
	}

	@Test
	void jsonTest() throws IOException {
		String content = "{\"id\":2,\"title\":\"2\",\"buyout\":200,\"minBid\":50,\"buyoutTime\":1,\"archive\":true,\"bids\":[{\"id\":1,\"bid\":150,\"zonedDateTime\":null,\"lotId\":2,\"userId\":1}]}";
		assertThat(this.json.parse(content))
				.isEqualTo(LotDto.builder()
						.id(2)
						.title("2")
						.archive(true)
						.buyout(200)
						.buyoutTime(1)
						.minBid(50)
						.bids(List.of(BidDto.builder().bid(150).lotId(2).userId(1).id(1).build())).build());
		assertThat(this.json.parseObject(content).getBids()).isEqualTo(List.of(BidDto.builder().bid(150).lotId(2).userId(1).id(1).build()));
	}

	@Test
	void someTest() throws Exception {
		given(this.lotService.getOneLot(1)).willReturn(LotDto.builder()
				.id(1)
				.title("1")
				.archive(true)
				.buyout(200)
				.buyoutTime(1)
				.minBid(50)
				.bids(List.of(BidDto.builder().bid(100).lotId(1).userId(1).id(1).build())).build());
		this.mockMvc.perform(get("/lots/1").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("bids[0].bid").value(100));
	}

}
