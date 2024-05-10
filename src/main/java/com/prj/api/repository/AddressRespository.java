package com.prj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.Address;

public interface AddressRespository extends JpaRepository<Address, Long> {

}
