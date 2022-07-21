package com.maheshd.user.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.maheshd.user.entity.UserEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

	Long id;
	
	@NotEmpty(message = "Username cannot be null or empty")
	String username;
	
	@NotEmpty(message = "Password cannot be null or empty")
	String password;
	
	List<AccountDTO> accounts = new ArrayList<>();

	public UserDTO dto(UserEntity entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.password = entity.getPassword();
		return this;
	}
	
	public UserEntity entity() {
		UserEntity entity = new UserEntity();
		entity.setId(this.getId());
		entity.setUsername(this.getUsername());
		entity.setPassword(this.getPassword());
		return entity;
	}
}
