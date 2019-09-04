package com.banking.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.when;

//import org.junit.Before;
import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;

import com.banking.account.Account;
//import com.banking.dao.AccountDao;
//import com.banking.user.User;

public class AccountServicesTest {

	private AccountServices aS = new AccountServices();

	/*
	 * @Before public void setup() { Account a1 = new Account("6-0", "Checking");
	 * when(accD.get("6-0")).thenReturn(a1); when(accD.get("5-2")).thenReturn(null);
	 * when(accD.get(null)).thenReturn(null);
	 * 
	 * Account a2 = new Account("5-0", "Savings", 150); User u1 = new User(5,
	 * "user1", "pass1"); when(accD.save(a2, u1)).thenReturn(true);
	 * when(accD.save(a2, null)).thenReturn(false); }
	 */
	@Test
	public void getAccountByValidId() {
		Account expected = new Account("5-0", "checking", 340);
		Account actual = aS.get("5-0");
		assertEquals(expected, actual);
	}

	@Test
	public void getAccountInvalidId() {
		assertNull(aS.get("59-2"));
	}

	@Test
	public void withdrawNegativeAmountReturnsFalse() {
		Account test = new Account("7-0", "savings");
		boolean actual = aS.withdrawMoney(test.getAccountNumber(), -10);
		assertFalse(actual);
	}
	
	@Test
	public void canNotWithdrawFromNullAccount() {
		boolean actual = aS.withdrawMoney(null, 500);
		assertFalse(actual);
	}
	
	
}
