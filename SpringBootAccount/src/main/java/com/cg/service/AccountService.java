package com.cg.service;

import java.util.List;

import com.cg.dto.AccountForm;
import com.cg.dto.CustomerForm;
import com.cg.entity.Account;
import com.cg.exceptions.AccountException;
import com.cg.exceptions.AgeException;
import com.cg.exceptions.CustomerException;

public interface AccountService {
	
	public String addAccount(AccountForm account)throws CustomerException;
	public String addCustomer(CustomerForm custForm) throws AgeException;
}
