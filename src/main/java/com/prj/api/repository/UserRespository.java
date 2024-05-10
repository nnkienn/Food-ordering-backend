package com.prj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.User;

public interface UserRespository extends JpaRepository<User,Long> {
	public User findByEmail(String username);
}
