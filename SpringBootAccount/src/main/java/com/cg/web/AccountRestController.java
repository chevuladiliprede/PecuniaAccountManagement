package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.AccountForm;
import com.cg.dto.AccountMessage;
import com.cg.dto.CustomerForm;
import com.cg.entity.Account;
import com.cg.exceptions.AccountException;
import com.cg.exceptions.AgeException;
import com.cg.exceptions.CustomerException;
import com.cg.service.AccountService;
import com.cg.util.AccountConstants;


@RestController
@CrossOrigin(origins= {"http://localhost:4200"})
public class AccountRestController {

	@Autowired
	public AccountService service;

	@PostMapping("/addaccount")
	public AccountMessage addAccount(@RequestBody AccountForm accountForm) throws AccountException, CustomerException {
		try {
			String accId=service.addAccount(accountForm);
			return new AccountMessage(AccountConstants.ACCOUNT_CREATED + AccountConstants.GENERATED_ACCOUNT+accId);

		} catch (DataIntegrityViolationException ex) {
			throw new AccountException(AccountConstants.ID_ALREADY_EXISTS);
		}
		
	}
	
	@PostMapping("/addcustomer")
	public AccountMessage addCustomer(@RequestBody CustomerForm custForm) throws CustomerException, AgeException {
		
			String custID = service.addCustomer(custForm);

			
		    return new AccountMessage(AccountConstants.CUSTOMER_CREATED+ AccountConstants.GENERATED_CUSTOMER+ custID);
	  
	}
	}


