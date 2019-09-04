package com.banking.services;

import java.util.List;
import java.util.Scanner;

import com.banking.account.Account;
import com.banking.dao.AccountDao;
import com.banking.user.User;

public class AccountServices {
	private static AccountDao accD = new AccountDao();

	private static Scanner scan = new Scanner(System.in);
	private static UserServices uS;
	
	private static String createAccId(User u) {
		int userId = u.getId();
		int count;
		List<String> accList = uS.getAllAccounts(userId);
		if (accList.size() > 0) {
			String lastAcc = accList.get(accList.size() - 1);
			count = Integer.parseInt(lastAcc.substring(lastAcc.length() - 1));
			count++;
		} else {
			count = 0;
		}
		String accId = userId + "-" + count;
		return accId;
	}

	public boolean createNewAccount(List<Account> accs, User masterUser) {
		System.out.println("Please name your new account; Limit: 10 characters\nOr 'quit' to quit");
		String input = scan.nextLine();
		inputloop: while (true) {
			if (input.length() <= 0 || input.length() > 10) {
				System.out.println(
						"Inproper length, please enter a name between 1-10 characters long, or 'quit' to quit");
				input = scan.nextLine();
				continue;
			}
			if (input.equals("quit")) {
				return false;
			} else {
				break inputloop;
			}
		}
		String accountId = createAccId(masterUser);
		Account newAccount = new Account(accountId, input);
		accD.save(newAccount, masterUser);
		accs.add(newAccount);
		System.out
				.println("Sucessfully created new account\nAccount Number: " + accountId + "\tAccount Name: " + input);
		return true;
	}

	public boolean withdrawMoney(String accNumber, float amount) {
		return accD.withdrawMoney(accNumber, amount);
	}

	public Account get(String accNo) {
		return accD.get(accNo);
	}

	public void addMoney(String accNo, float amt) {
		accD.addMoney(accNo, amt);
	}

	public boolean transferMoney(String acc1, String acc2, float amt) {
		return accD.transferMoney(acc1, acc2, amt);
	}

	public void delete(Account a) {
		accD.delete(a);
	}
}
