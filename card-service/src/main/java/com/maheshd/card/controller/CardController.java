package com.maheshd.card.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maheshd.card.dto.CardDTO;
import com.maheshd.card.entity.CardEntity;
import com.maheshd.card.repo.CardRepository;

@RestController
@RequestMapping(value = "/api/card")
public class CardController {

	@Autowired
	CardRepository cardRepository;
	
	@GetMapping(value = "/{accountId}")
	public ResponseEntity<CardDTO> getCardForAccount(@Valid @PathVariable("accountId") Long accountId) {
		
		CardEntity entity = cardRepository.findByAccountId(accountId);
		return new ResponseEntity<CardDTO>(new CardDTO().dto(entity), HttpStatus.OK);
	}

	@PostMapping("/{accountId}")
	@Transactional
	public ResponseEntity<CardDTO> addCardForAccount(@PathVariable("accountId") Long accountId, 
			@Validated @RequestBody CardDTO cardDto) {
		cardDto.setAccountId(accountId);
		CardEntity entity = cardDto.entity();
		entity = cardRepository.save(entity);
		return new ResponseEntity<CardDTO>(new CardDTO().dto(entity), HttpStatus.CREATED);
	}

}
