package com.maheshd.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maheshd.account.dto.CardDTO;

@FeignClient(name = "card-service", path = "/api/card")
public interface CardFeignClient {

	@GetMapping("/{accountId}")
	CardDTO getCardForAccount(@PathVariable("accountId") Long accoutId);
	
	@PostMapping("/{accountId}")
	CardDTO addCardForAccount(@PathVariable("accountId") Long accountId, @RequestBody CardDTO card);
}
