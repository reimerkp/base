package com.banking.dao;

import java.util.List;

import com.banking.account.Account;
import com.banking.user.User;

public interface AccountDaoInterface {

	Account get(String id);

	List<Account> getAll();


	boolean save(Account a ,User u);

	boolean addMoney(String accId, float amount);
	
	boolean withdrawMoney(String accId, float amount);
	
	boolean delete(Account a);
	
	boolean transferMoney(String a, String b, float amount);
}
