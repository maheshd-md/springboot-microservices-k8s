package com.maheshd.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maheshd.user.dto.AccountDTO;
import com.maheshd.user.dto.UserDTO;
import com.maheshd.user.entity.UserEntity;
import com.maheshd.user.repo.UserRepository;
import com.maheshd.user.response.ErrorResponse;
import com.maheshd.user.response.SuccessReponse;
import com.maheshd.user.response.UserReponse;
import com.maheshd.user.service.client.AccountFeignClient;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AccountFeignClient accountFeignClient;

	@GetMapping(value = "/{username}")
	public ResponseEntity<UserReponse> getUserByUsername(@Valid @PathVariable String username) {

		Optional<UserEntity> userOp = userRepository.findByUsername(username);
		if (userOp.isPresent()) {
			UserDTO userDto = new UserDTO().dto(userOp.get());
			List<AccountDTO> accountDtos = accountFeignClient.getAccountsByUserId(userDto.getId());
			userDto.setAccounts(accountDtos);
			UserReponse response = new SuccessReponse<UserDTO>("User fetched successfully!", userDto);
			return new ResponseEntity<UserReponse>(response, HttpStatus.OK);
		}

		UserReponse response = new ErrorResponse("User not found: " + username);
		return new ResponseEntity<UserReponse>(response, HttpStatus.ACCEPTED);
	}

	@GetMapping
	public ResponseEntity<UserReponse> getAllUsers() {

		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
		List<UserDTO> responseUserDtos = new ArrayList<>();
		users.forEach(user -> {
			UserDTO userDto = new UserDTO().dto(user);
			List<AccountDTO> accountDtos = accountFeignClient.getAccountsByUserId(userDto.getId());
			userDto.setAccounts(accountDtos );
			responseUserDtos.add(userDto);
		});
		UserReponse response = new SuccessReponse<List<UserDTO>>("Users fetched successfully!",	responseUserDtos);
		return new ResponseEntity<UserReponse>(response, HttpStatus.OK);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<UserReponse> addUser(@Validated @RequestBody UserDTO userDto) {

		// Encode password before saving
		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		UserEntity userEntity = userRepository.save(userDto.entity());
		UserDTO responseUserDto = new UserDTO().dto(userEntity);
		List<AccountDTO> responseAccountDtos = new ArrayList<>();
		for (AccountDTO accountDto : userDto.getAccounts()) {
			accountDto.setUserId(userEntity.getId());
			accountDto = accountFeignClient.addAccountForUser(accountDto);
			responseAccountDtos.add(accountDto);
		}
		responseUserDto.setAccounts(responseAccountDtos);
		UserReponse response = new SuccessReponse<UserDTO>("User added successfully!", responseUserDto);
		return new ResponseEntity<UserReponse>(response, HttpStatus.OK);
	}

}
