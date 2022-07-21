package com.maheshd.user.dto;

import javax.validation.constraints.NotEmpty;

import com.maheshd.user.entity.CardEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CardDTO {

	Long id;
	
	Long accountId;
	
	@NotEmpty(message = "Card number cannot be null or empty")
	String cardNumber;
	
	public CardDTO dto(CardEntity entity) {
		this.id = entity.getId();
		this.cardNumber = entity.getCardNumber();
		this.accountId = entity.getAccountId();
		return this;
	}
	
	public CardEntity entity() {
		CardEntity entity = new CardEntity();
		entity.setId(this.getId());
		entity.setCardNumber(this.getCardNumber());
		entity.setAccountId(this.getAccountId());
		return entity;
	}
}
