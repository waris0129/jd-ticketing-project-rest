package com.cybertek.controller;

import com.cybertek.annotation.DefaultExceptionMessage;
import com.cybertek.dto.MailDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.ConfirmationToken;
import com.cybertek.entity.ResponseWrapper;
import com.cybertek.entity.UserEntity;
import com.cybertek.entity.common.AuthenticationRequest;
import com.cybertek.exception.TicketingException;
import com.cybertek.mapper.Mapper;
import com.cybertek.service.ConfirmationTokenService;
import com.cybertek.service.UserService;
import com.cybertek.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@Tag(name = "Authentication Controller",description = "Authenticate API")
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
	@Value("${app.local-url}")
	private String BASE_URL;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private Mapper mapper;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private ConfirmationTokenService confirmationTokenService;

	@PostMapping("/authenticate")
//	@DefaultExceptionMessage(defaultMessage = "Bad Credentials")
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

	@DefaultExceptionMessage(defaultMessage = "something went wrong, try again")
	@PostMapping("/create-user")
	@Operation(summary = "Create new account")
	private ResponseEntity<ResponseWrapper> doRegister(@RequestBody UserDTO userDTO) throws TicketingException {

		UserDTO createdUser = userService.save(userDTO);

		sendEmail(createEmail(createdUser));

		return ResponseEntity.ok(new ResponseWrapper("user has been created",createdUser));

	}

	@DefaultExceptionMessage(defaultMessage = "Failed to confirm email, please try again!")
	@GetMapping("/confirmation")
	@Operation(summary = "Confirm account")
	private ResponseEntity<ResponseWrapper> confirmEmail(@RequestParam("token") String token) throws TicketingException {

		ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);
		UserDTO confirmedUserDto = userService.confirm(confirmationToken.getUserEntity());
		confirmationTokenService.delete(confirmationToken);

		return ResponseEntity.ok(new ResponseWrapper("User has been confirmed!",confirmedUserDto));

	}

	private MailDTO createEmail(UserDTO userDTO){

		UserEntity userEntity = mapper.convert(userDTO,new UserEntity());

		ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);

		confirmationToken.setIsDeleted(false);

		ConfirmationToken createdConfirmationToken = confirmationTokenService.save(confirmationToken);

		return MailDTO
				.builder()
				.emailTo(userEntity.getUsername())
				.token(createdConfirmationToken.getToken())
				.subject("Confirm Registration")
				.message("To confirm your account, please click here:")
				.url(BASE_URL+"/confirmation?token=")
				.build();


	}

	private void sendEmail(MailDTO mailDTO){

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mailDTO.getEmailTo());
		mailMessage.setSubject(mailDTO.getSubject());
		mailMessage.setText(mailDTO.getMessage()+mailDTO.getUrl()+mailDTO.getToken());

		confirmationTokenService.sendEmail(mailMessage);



	}



}

































