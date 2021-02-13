package com.cybertek.controller;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.ResponseWrapper;
import com.cybertek.entity.UserEntity;
import com.cybertek.entity.common.AuthenticationRequest;
import com.cybertek.exception.TicketingException;
import com.cybertek.mapper.Mapper;
import com.cybertek.service.UserService;
import com.cybertek.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class LoginController {
	
//	@RequestMapping(value = {"/login","/"})
//	public String login(){
//		return "login";
//	}
//
//	@RequestMapping("/welcome")
//	public String welcome(){
//		return "welcome";
//	}

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private Mapper mapper;
	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<ResponseWrapper> doLogin(@RequestBody AuthenticationRequest request) throws TicketingException {

		String password = request.getPassword();
		String username = request.getUsername();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
		authenticationManager.authenticate(authentication);

		UserDTO foundUserDto = userService.findByUserName(username);

		UserEntity convertedUserEntity = mapper.convert(foundUserDto,new UserEntity());

		if(!foundUserDto.isEnabled()){
			throw new TicketingException("please verify your user");
		}

		String jwtToken = jwtUtil.generateToken(convertedUserEntity,username);

		return ResponseEntity.ok(new ResponseWrapper("login successful",jwtToken));

	}



}
