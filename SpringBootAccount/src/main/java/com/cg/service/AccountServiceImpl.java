package com.cg.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dao.AccountDao;
import com.cg.dao.CustomerDao;
import com.cg.dao.TxDao;
import com.cg.dto.AccountForm;
import com.cg.dto.CustomerForm;
import com.cg.entity.AccTransaction;
import com.cg.entity.Account;
import com.cg.entity.Customer;
import com.cg.exceptions.AccountException;
import com.cg.exceptions.AgeException;
import com.cg.exceptions.CustomerException;
import com.cg.util.AccountConstants;

@Service("accountser")
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDao accdao;
	@Autowired
	private CustomerDao custdao;
	@Autowired
	private TxDao txdao;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public String addAccount(AccountForm accountForm) throws CustomerException {
		Account account = new Account();
		Optional<Customer> optcustomer=custdao.findById(accountForm.getCustomerID());
		if (!optcustomer.isPresent())
			throw new CustomerException(AccountConstants.CUSTOMER_NOT_FOUND);
		LocalDateTime now = LocalDateTime.now();
		String id = accountForm.getAccountName()+accountForm.getCustomerID();
		account.setAccountId(id);
		account.setAccountBalance(accountForm.getBalance());
		account.setAccountName(accountForm.getAccountName());
		account.setAccountStatus(AccountConstants.ACTIVE);
		account.setLastUpdated(LocalDate.now());
		account.setCustomer(optcustomer.get());
		Account acc=accdao.save(account);
		accdao.flush();
		AccTransaction tx=new AccTransaction();
		tx.setTransAmount(accountForm.getBalance());
		tx.setTransDate(LocalDate.now());
		tx.setTransType(AccountConstants.CREDIT);
		tx.setTransDescription(AccountConstants.OPEN_DESC);
		tx.setAccount(account);
		txdao.save(tx);
		return acc.getAccountId();
			}

	@Override
	public String addCustomer(CustomerForm custForm) throws AgeException {
		Customer cust = new Customer();
		LocalDateTime now = LocalDateTime.now();
		LocalDate now1=LocalDate.now();
		String custID = AccountConstants.EMPTY+ now.getYear()+ now.getMonthValue()+now.getDayOfMonth()+now.getHour()+now.getMinute()+now.getSecond();
		cust.setCustomerId(custID);
		cust.setCustomerName(custForm.getCustomerName());
		cust.setCustomerDob(custForm.getCustomerDob());
		cust.setCustomerAadhar(custForm.getCustomerAadhar());
		cust.setCustomerAddress(custForm.getCustomerAddress());
		cust.setCustomerContact(custForm.getCustomerContact());
		cust.setCustomerPan(custForm.getCustomerPan());
		cust.setPassword(custForm.getPassword());
		cust.setCustomerGender(custForm.getCustomerGender());
		cust.setRole(AccountConstants.ROLE_USER);
		int age=Math.abs(Period.between(now1, custForm.getCustomerDob()).getYears());
		
		if(age>=18) {
			custdao.save(cust);
			return custID;
		}
		else {
			throw new AgeException();
		}
		
		
	}

	}


