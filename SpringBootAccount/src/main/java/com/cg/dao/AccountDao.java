package com.cg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Account;
import com.cg.entity.Customer;


@Repository
public interface AccountDao extends JpaRepository<Account,String> {

	
	
}
