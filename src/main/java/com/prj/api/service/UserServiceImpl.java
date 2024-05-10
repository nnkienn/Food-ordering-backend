package com.prj.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.config.JwtProvider;
import com.prj.api.model.User;
import com.prj.api.repository.UserRespository;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRespository userRepo;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = findUserByEmail(email);
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new Exception("user not found");
		}
		return user;
		
	}

}
