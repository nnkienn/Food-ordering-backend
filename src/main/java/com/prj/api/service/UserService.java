package com.prj.api.service;


import com.prj.api.model.User;


public interface UserService {
public User findUserByJwtToken(String jwt)  throws Exception;
public User findUserByEmail(String email) throws Exception;
}
