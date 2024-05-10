package com.prj.api.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.config.JwtProvider;
import com.prj.api.model.Cart;
import com.prj.api.model.USER_ROLE;
import com.prj.api.model.User;
import com.prj.api.repository.CartRepository;
import com.prj.api.repository.UserRespository;
import com.prj.api.response.AuthResponse;
import com.prj.api.response.LoginRequest;
import com.prj.api.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRespository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

	@Autowired
	private CartRepository cartRepo;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createtUserHandler(@RequestBody User user) throws Exception {
		User isEmailExits = userRepo.findByEmail(user.getEmail());
		if (isEmailExits != null) {
			throw new Exception("email is already used with another account");

		}
		User createUser = new User();
		createUser.setEmail(user.getEmail());
		createUser.setFullName(user.getFullName());
		createUser.setRole(user.getRole());
		createUser.setPassword(passwordEncoder.encode(user.getPassword()));
		User saveUser = userRepo.save(createUser);

		Cart cart = new Cart();
		cart.setCustomer(saveUser);
		cartRepo.save(cart);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.genrateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(jwt);

		authResponse.setMessage("Register success");

		authResponse.setRole(saveUser.getRole());

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) throws Exception {

		String username = req.getEmail();
		String password = req.getPassword();

		Authentication authentication = autheticate(username, password);
		Collection<?extends GrantedAuthority>authorities = authentication.getAuthorities();

		String role= authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

		String jwt = jwtProvider.genrateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register success");
		authResponse.setRole(USER_ROLE.valueOf(role));
		return new ResponseEntity<>(authResponse,HttpStatus.OK);

	}

	private Authentication autheticate(String username, String password) {
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("invalid username ....");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password ....");
		};
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	}

}
