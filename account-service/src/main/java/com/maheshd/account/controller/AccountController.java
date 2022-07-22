package com.maheshd.account.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maheshd.account.dto.AccountDTO;
import com.maheshd.account.dto.CardDTO;
import com.maheshd.account.entity.AccountEntity;
import com.maheshd.account.repo.AccountRepository;
import com.maheshd.account.service.client.CardFeignClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CardFeignClient cardFeignClient;

	@GetMapping(value = "/{userId}")
	@CircuitBreaker(name = "circuitBreakerGetAccountsByUserId", fallbackMethod = "getAccountsByUserIdFallback")
	//@Retry(name = "retryGetAccountsByUserId", fallbackMethod = "getAccountsByUserIdFallback")
	//@RateLimiter(name = "rateLimiterGetAccountsByUserId", fallbackMethod = "getAccountsByUserIdFallback")
	//@Bulkhead(name = "bulkHeadGetAccountsByUserId", fallbackMethod = "getAccountsByUserIdFallback")
	public List<AccountDTO> getAccountsByUserId(@PathVariable("userId") Long userId) {
		List<AccountEntity> accounts = accountRepository.findByUserId(userId);
		List<AccountDTO> responseAccountDtos = new ArrayList<>();
		accounts.forEach(account -> {
			AccountDTO accountDto = new AccountDTO().dto(account);
			CardDTO cardDto = cardFeignClient.getCardForAccount(accountDto.getId());
			accountDto.setCard(cardDto);
			responseAccountDtos.add(accountDto);
		});
		return responseAccountDtos;
	}

	@PostMapping
	@Transactional
	public AccountDTO addAccountForUser(@Validated @RequestBody AccountDTO accountDto) {
		AccountEntity entity = accountDto.entity();
		accountRepository.save(entity);
		AccountDTO responseDto = new AccountDTO().dto(entity);
		CardDTO cardDto = cardFeignClient.addCardForAccount(responseDto.getId(), accountDto.getCard());
		responseDto.setCard(cardDto);
		return responseDto;
	}

	private List<AccountDTO> getAccountsByUserIdFallback(@PathVariable("userId") Long userId, Throwable t) {
		System.out.println("Fallback method called!");
		List<AccountEntity> accounts = accountRepository.findByUserId(userId);
		List<AccountDTO> responseAccountDtos = new ArrayList<>();
		accounts.forEach(account -> {
			AccountDTO accountDto = new AccountDTO().dto(account);
			// Return empty card dto in case of fallback
			CardDTO cardDto = new CardDTO();
			accountDto.setCard(cardDto);
			responseAccountDtos.add(accountDto);
		});
		return responseAccountDtos;
	}

}
