package com.maheshd.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(unique = true)
	@NotEmpty(message = "Username cannot be null or empty")
	String username;
	
	@Column
	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be emppty")
	String password;
	
}
