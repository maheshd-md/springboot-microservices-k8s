package com.maheshd.account.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.maheshd.account.entity.AccountEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AccountDTO {

	Long id;
	
	Long userId;
	
	@NotEmpty(message = "Bank name cannot be null or empty")
	String bankname;
	@NotEmpty(message = "Account number cannot be null or empty")
	String accountNumber;
	@Min(value = 0, message = "Account balance cannot be less than 0")
	@Max(value = Long.MAX_VALUE)
	Long accountBalance;
	CardDTO card = new CardDTO();
	
	public AccountDTO dto(AccountEntity entity) {
		this.id = entity.getId();
		this.accountNumber = entity.getAccountNumber();
		this.bankname = entity.getBankname();
		this.accountBalance = entity.getAccountBalance();
		this.userId = entity.getUserId();
		return this;
	}
	
	public AccountEntity entity() {
		AccountEntity entity = new AccountEntity();
		entity.setId(this.getId());
		entity.setAccountNumber(this.getAccountNumber());
		entity.setBankname(this.getBankname());
		entity.setAccountBalance(this.getAccountBalance());
		entity.setUserId(this.getUserId());
		return entity;
	}
}
