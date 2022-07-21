package com.maheshd.user.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maheshd.user.dto.AccountDTO;

@FeignClient(name = "account-service", path = "/api/account")
public interface AccountFeignClient {

	@GetMapping("/{userId}")
	List<AccountDTO> getAccountsByUserId(@PathVariable("userId") Long userId);
	
    @PostMapping
	AccountDTO addAccountForUser(@RequestBody AccountDTO accountDto);
}
