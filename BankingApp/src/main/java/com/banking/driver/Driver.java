package com.banking.driver;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.banking.account.Account;
import com.banking.encrypt.Encrypter;
import com.banking.services.AccountServices;
import com.banking.services.UserServices;
import com.banking.user.User;

public class Driver {
	private static Scanner scan = new Scanner(System.in);

	private static User masterUser;
	private static AccountServices aS = new AccountServices();
	private static UserServices uS = new UserServices();
	private static boolean login()
			throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		Encrypter encrypt = new Encrypter();
		boolean check = true;
		System.out.print("Please enter your UserName: ");
		String uname = scan.nextLine();
		masterUser = uS.getByUsername(uname);

		while (null == masterUser && check) {
			System.out.print("No such user exists\nPlease enter your UserName or 0 to quit: ");
			uname = scan.nextLine();
			if (uname.equals("0")) {
				check = false;
				continue;
			} else {
				masterUser = uS.getByUsername(uname);
			}

		}
		if (!check) {
			System.out.println("Quit login");
			return false;
		}
		if (null != masterUser) {
			check = true;
			while (check) {
				System.out.print("Please enter your password: ");
				String pwd = scan.nextLine();
				if (pwd.equals(encrypt.decrypt(masterUser.getPassword()))) {
					System.out.println("login successfull!");
					return true;
				} else if (pwd.equals("0")) {
					check = false;
					continue;
				} else {
					System.out.println("Incorrect password, try again or 0 to quit");
					continue;
				}
			}
			System.out.println("Quit login");
			return false;
		}
		return false;
	}

	private static boolean withdrawMoney(String accountNumber, Account a) {
		boolean withdrawing = true;
		boolean checked = false;
		while (withdrawing) {
			float am = a.getAmount();
			if (am == 0) {
				System.out.println("You have no money in this account");
				return false;
			}
			String amount = Float.toString(am);
			System.out.println("How much would you like to withdrawl from Account no " + accountNumber
					+ "?\nCurrent amount: " + amount);
			String ans = scan.nextLine().trim();

			if (ans.isEmpty()) {
				boolean emptyCheck = true;
				while (emptyCheck) {
					System.out.println("Please enter an actual amount you would like to withdraw, or \"quit\" to quit");
					ans = scan.nextLine().trim();
					if (ans.equals("quit")) {
						return false;
					}
					checked = checkInputFloat(ans);
					emptyCheck = !checked;
				}

			} else {
				checked = checkInputFloat(ans);
				while (!checked) {
					System.out.println("Please enter an actual amount you would like to withdraw, or \"quit\" to quit");
					ans = scan.nextLine();
					if (null != ans && ans.equals("quit")) {
						return false;
					} else
						checked = checkInputFloat(ans);
				}
			}
			if (Float.parseFloat(ans) > am) {
				System.out.println("Can not witdraw more than what you have in your account.");
				continue;
			} else {
				float correctAmount = Float.parseFloat(ans);
				if (correctAmount < 0) {
					System.out.println("Can not withdraw a negative value");
					continue;
				}
				a.withdrawMoney(correctAmount);
				aS.withdrawMoney(accountNumber, correctAmount);
				System.out.println("You withdrew " + correctAmount + " from Account number " + accountNumber);
				boolean continueCheck = true;
				while (continueCheck) {
					System.out.println("Would you like to withdraw more? '1' for yes or '2' for no");
					ans = scan.nextLine().trim();
					int input = checkInput1or2(ans);
					switch (input) {
					case 1:
						continueCheck = false;
						break;
					case 2:
						continueCheck = false;
						withdrawing = false;
						continue;
					default:
						System.out.println("That is not a choice...");
						continue;
					}

				}
			}
		}
		return true;
	}

	private static boolean checkInputFloat(String ans) {
		if (ans.isEmpty() || null == ans)
			return false;
		else {
			for (char c : ans.toCharArray()) {
				if (Character.isAlphabetic(c)) {
					return false;
				}
			}
			return true;
		}
	}

	private static int checkInput1or2(String input) {
		if (input.length() > 1)
			return -1;
		else {
			switch (input) {
			case "1":
				return 1;
			case "2":
				return 2;
			default:
				return -1;
			}
		}
	}

	private static int checkInputUpTo5(String input) {
		if (input.length() > 1)
			return -1;
		else {
			switch (input) {
			case "1":
				return 1;
			case "2":
				return 2;
			case "3":
				return 3;
			case "4":
				return 4;
			case "5":
				return 5;
			default:
				return -1;
			}
		}
	}

	private static boolean depositMoney(String accountNo) {
		boolean check = true;
		String input = "";
		while (check) {
			System.out
					.println("How much would you like to deposit into account no: " + accountNo + " or 'quit' to quit");
			input = scan.nextLine();
			if (null == input) {
				System.out.println("Please enter an amount or 'quit'");
				continue;
			} else {
				if (input.equals("quit")) {
					return false;
				}
				boolean checkInput = checkInputFloat(input);
				if (checkInput) {
					float in = Float.parseFloat(input);
					if (in < 0) {
						System.out.println("You can't add a negative number");
						continue;
					} else {
						aS.addMoney(accountNo, in);
						System.out.println("Successfully added " + in + " to account number: " + accountNo);
					}
				} else {
					System.out.println("Please enter an actual number");
					continue;
				}
			}
			System.out.println("Would you like to deposit more money?\n1: yes \t2: no");
			input = scan.nextLine();
			int checkIn = checkInput1or2(input);
			inputloop: while (true) {
				switch (checkIn) {
				case 1:
					break inputloop;
				case 2:
					check = false;
					break inputloop;
				default:
					System.out.println("Please enter either 1 or 2");
					input = scan.nextLine();
					checkIn = checkInput1or2(input);
					continue;
				}

			}
		}
		return true;

	}

	private static String getAccountNumber(List<Account> accs) {
		String accountNo = null;
		boolean checkAccount = true;
		while (checkAccount) {
			System.out.println("Which account would you like to use? 0 to quit");
			for (Account ac : accs) {
				System.out.println(ac.toString());
			}
			String accountChoice = scan.nextLine();
			if (null == accountChoice) {
				System.out.println("Please enter a valid account number");
				continue;
			} else if (accountChoice.equals("0")) {
				return "quit";
			} else {
				for (Account ac : accs) {
					if (accountChoice.equals(ac.getAccountNumber())) {
						accountNo = ac.getAccountNumber();
					}
				}
			}
			if (accountNo == null) {
				System.out.println("No such account exists. Please choose an actual account");
			} else {
				checkAccount = false;
			}
		}

		return accountNo;
	}

	public static List<Account> createAccountList() {
		List<String> accIds = uS.getAllAccounts(masterUser.getId());
		List<Account> accs = new ArrayList<Account>();
		for (String s : accIds) {
			Account a = aS.get(s);
			accs.add(a);
		}
		return accs;
	}

	public static void main(String[] args) {

		boolean running = true;
		System.out.println("\t\tWelcome to FakeBank!");
		while (running) {
			System.out.println("\t     What would you like to do?");
			System.out.println("\t  Log In: 1   Create new Bank Account: 2    Quit: 3");
			String choice = scan.nextLine();
			if (null != choice && choice.equals("3")) {
				running = false;
				continue;
			}
			int userChoice = checkInput1or2(choice);
			switch (userChoice) {
			case 1:
				boolean loggedIn = false;
				try {
					loggedIn = login();
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchPaddingException
						| NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException
						| IllegalBlockSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (!loggedIn) {
					running = false;
					continue;
				} else {

					boolean checkInput = true;
					int input = 0;
					String ans = "";
					while (checkInput) {
						List<Account> accs = createAccountList();
						System.out.println(
								"Would you like to access your accounts, create a new one, delete your account, or quit?"
										+ "\n1: to access your accounts\t2: to create a new account\t3: delete your account\t4: Quit");
						ans = scan.nextLine();
						if (null != ans && ans.equals("5")) {
							running = false;
							input = 5;
							break;
						}
						input = checkInputUpTo5(ans);
						if (input == -1) {
							System.out.println("Please enter either '1','2','3', or '4' to quit");
							continue;
						}
						if (input == 1) {
							if (accs.size() > 0) {
								if (accs.size() == 1)
									System.out.println("You currently have 1 active account");
								else
									System.out.println("You currently have " + accs.size() + " active accounts");
								boolean accountManip = true;
								while (accountManip) {
									System.out.println("\tWhat do you want to do now?");
									System.out.println(
											"1: Deposit money \t2: Withdraw money \t3: transfer money\t4: Delete a bank account\t5: Quit");
									String accountChoice = "";
									ans = scan.nextLine();
									inputloop: while (true) {
										switch (ans) {
										case "1":
											if (accs.size() > 1) {
												accountChoice = getAccountNumber(accs);
											} else
												accountChoice = accs.get(0).getAccountNumber();
											boolean isDeposited = depositMoney(accountChoice);
											if (isDeposited) {
												System.out.println("Deposit finished succesfully!");
											} else {
												System.out.println("We are sorry we could not complete your deposit");
											}
											break inputloop;
										case "2":
											boolean withdrawWorked = false;
											if (accs.size() > 1) {

												accountChoice = getAccountNumber(accs);
												if (accountChoice.equals("quit")) {
													break inputloop;
												}

												withdrawWorked = withdrawMoney(accountChoice,
														aS.get(accountChoice));

											} else {
												withdrawWorked = withdrawMoney(accs.get(0).getAccountNumber(),
														accs.get(0));
											}
											if (!withdrawWorked) {
												System.out.println("We are sorry we could not complete your withdraw.");
											} else {
												System.out.println("Withdraw completed successfully!");
											}

											break inputloop;
										case "3":
											Account from = null;
											if (accs.size() < 1) {
												System.out.println(
														"You do not have enough accounts to initiate a transfer");
												break inputloop;
											}
											moneyCheck: while (true) {
												accountChoice = getAccountNumber(accs);
												if (accountChoice.equals("quit")) {
													break inputloop;
												}
												from = aS.get(accountChoice);
												if (from.getAmount() == 0) {
													System.out.println(
															"You have no money to transfer, try another account\n");
													continue;
												} else
													break moneyCheck;
											}
											boolean wantsToQuit = false;
											float amount = 0;
											checkLoop: while (true) {
												System.out.println(
														"How much would you like to transfer? 'quit' to quit\nCurrent amount: "
																+ from.getAmount());
												String in = scan.nextLine().toLowerCase();
												if (null != in && in.equals("quit")) {
													wantsToQuit = true;
													break checkLoop;
												}
												if (!checkInputFloat(in)) {
													System.out.println("Please enter an actual amount\n");
													continue;
												} else {
													amount = Float.parseFloat(in);
													if (amount > from.getAmount()) {
														System.out.println("You can not transfer more than you have\n");
														continue;
													} else {
														break checkLoop;
													}
												}
											}
											if (wantsToQuit) {
												break inputloop;
											}
											String too = getTransferAccount(from);
											if (too.equals("quit")) {
												break inputloop;
											} else {
												boolean didWork = false;
												checkLoop: while (true) {
													System.out.println("Are you sure you want to transfer $" + amount
															+ " " + "\nfrom account number: " + from.getAccountNumber()
															+ " to account number: " + too + "? 'y' or 'n'");
													String in = scan.nextLine();
													switch (in) {
													case "y":
														didWork = aS.transferMoney(from.getAccountNumber(),
																too, amount);
														break checkLoop;
													case "n":
														break checkLoop;
													default:
														System.out.println("Please enter either 'y' or 'n'");
														continue;
													}
												}
												if (didWork) {
													System.out.println("Congrats on completing your transfer!\n");
												} else {
													System.out.println(
															"We are sorry we were not able to complete your transfer");
												}

											}
											break inputloop;

										case "4":
											if (accs.size() > 1) {
												accountChoice = getAccountNumber(accs);
												if (accountChoice.equals("quit")) {
													break inputloop;
												}
											} else
												accountChoice = accs.get(0).getAccountNumber();
											deleteCheck: while (true) {
												System.out.println("Are you sure you want to delete account number: "
														+ accountChoice + "?\n'y' or 'n'");
												String in = scan.nextLine();
												switch (in) {
												case "y":
													aS.delete(aS.get(accountChoice));
													accountManip = false;
													break inputloop;
												case "n":
													System.out.println("Leaving delete account..\n");
													break deleteCheck;
												default:
													System.out.println("\nPlease enter either 'y' or 'n'");
													continue;
												}

											}

											break inputloop;

										case "5":
											accountManip = false;
											break inputloop;
										default:
											System.out.println("Please enter either '1','2', or '3'\n");
											System.out.println("1: Deposit money \t2: Withdraw money \t3: Quit");
											ans = scan.nextLine();
											continue;
										}
									}
									accs = createAccountList();
									if (!accountManip) {
										continue;
									}
									boolean continueCheck = true;

									while (continueCheck) {
										System.out.println(
												"Would you like to continue using your accounts? '1' for yes or '2' for no");
										ans = scan.nextLine().trim();
										int in = checkInput1or2(ans);
										switch (in) {
										case 1:
											continueCheck = false;
											break;
										case 2:
											accountManip = false;
											continueCheck = false;
											continue;
										default:
											System.out.println("That is not a choice...");
											continue;
										}

									}
								}

							} else { // accs.size == 0
								System.out.println("You have no active accounts. Would you like to create one?"
										+ "\n1: yes \t2: no");
								ans = scan.nextLine();
								int checker = checkInput1or2(ans);
								boolean checkIfCreated = false;
								inputloop: while (true) {
									switch (checker) {
									case 1:
										checkIfCreated = aS.createNewAccount(accs, masterUser);
										break inputloop;
									case 2:
										break inputloop;
									default:
										System.out.println("Please enter either 1 or 2");
										ans = scan.nextLine();
										checker = checkInput1or2(ans);
									}
								}
								if (checker != 2 && checkIfCreated) {
									System.out.println("Congrats on creating a new account!");
								} else if (!checkIfCreated && checker == 1) {
									System.out.println("We are sorry we could not complete creating a new account.");
								}
								continue;
							}
						} else if (input == 2) {
							boolean checkIfCreated = aS.createNewAccount(accs, masterUser);
							if (checkIfCreated) {
								System.out.println("Congrats on creating a new account!");
							} else {
								System.out.println("We are sorry we could not complete creating a new account.");
							}

						} else if (input == 3) {
							boolean doubleCheck = true;
							while (doubleCheck) {
								System.out.println("Are you sure you would like to delete your account? "
										+ "This will delete your entire bank account and all affiliated accounts.\n"
										+ "'y' or 'n'");
								ans = scan.nextLine();
								if (null != ans && ans.length() > 0 && ans.length() < 2) {
									switch (ans) {
									case "y":
										uS.delete(masterUser);
										doubleCheck = false;
										checkInput = false;
										break;
									case "n":
										doubleCheck = false;
										System.out.println("Cancelled deleting account\n");
										break;
									default:
										System.out.println("Please enter either 'y' or 'n'\n");
										continue;
									}
								} else {
									System.out.println("Please enter either 'y' or 'n'\n");
								}
							}

						} else {
							checkInput = false;
							continue;
						}
					}
				}
				break;
			case 2:
				try {
					createNewUser();
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchPaddingException
						| NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException
						| IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			default:
				System.out.println("\tPlease enter either 1 or 2");
			}

		}
		System.out.println("\t  Thank you for using FakeBank");
		scan.close();

	}

	private static boolean createNewUser()
			throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		Encrypter encrypt = new Encrypter();
		String userInput = "";
		boolean wantsToQuit = false;
		System.out.println("During creation enter 'quit' if you would like to stop creating an account\n");
		userNameInputLoop: while (true) {
			System.out.println("Please enter a username you would like to use");
			userInput = scan.nextLine().trim();
			if (userInput == null || userInput.length() == 0) {
				continue;
			} else if (userInput.length() > 20) {
				System.out.println("Username length too long, please keep under 20 characters");
				continue;
			} else if (userInput.equals("quit")) {
				wantsToQuit = true;
				break userNameInputLoop;
			} else {
				User u = uS.getByUsername(userInput);
				if (u != null) {
					System.out.println("Username already taken");
					continue;
				} else {
					break userNameInputLoop;
				}
			}

		}
		if (wantsToQuit) {
			System.out.println("Quitting account Creation..\n");
			return false;
		}
		String pwdInput = "";
		passwordInputLoop: while (true) {
			System.out.println(
					"Please enter a password, must " + "be > 4 and  < 20 characters and have at least 1 number");
			pwdInput = scan.nextLine().trim();
			if (pwdInput.length() < 4 || pwdInput.length() > 20) {
				System.out.println("Incorrect amount of characters");
				continue;
			} else if (pwdInput.equals("quit")) {
				wantsToQuit = true;
				break passwordInputLoop;
			} else {
				int numCounter = 0;
				for (char x : pwdInput.toCharArray()) {
					if (Character.isDigit(x))
						numCounter++;
				}
				if (numCounter == 0) {
					System.out.println("Password must have at least 1 number");
					continue;
				} else {
					break passwordInputLoop;
				}
			}

		}
		if (wantsToQuit) {
			System.out.println("Quitting account Creation..\n");
			return false;
		}
		String encryptedPwd = encrypt.encrypt(pwdInput);
		User newUser = new User(0, userInput, encryptedPwd);
		uS.save(newUser);
		System.out.println("Congrats on creating a new Account!\nUserName: " + userInput + ", Password: " + pwdInput);
		return true;
	}

	private static String getTransferAccount(Account from) {
		List<User> users = uS.getAll();
		List<String> usernames = new ArrayList<String>();
		for (User u : users) {
			usernames.add(u.getUsername());
		}
		String uname;
		List<String> accounts = null;
		checkLoop: while (true) {
			System.out.println("Which user would you like to transfer too? Enter 'quit' to quit");
			for (String u : usernames) {
				System.out.println("Username: " + u);
			}
			uname = scan.nextLine();
			if (usernames.contains(uname)) {
				User transferUser = null;
				for (User u : users) {
					if (u.getUsername().equals(uname)) {
						transferUser = u;
						accounts = uS.getAllAccounts(transferUser.getId());

						if (accounts.size() == 0) {
							System.out.println("This user has no active accounts, please pick another one");
							usernames.remove(u.getUsername());
							break;
						} else {
							accounts.remove(from.getAccountNumber());
							if (accounts.size() == 0) {
								System.out.println(
										"You do not have another account you can transfer too, please pick a different user or 'quit' to quit");
								usernames.remove(u.getUsername());
								break;
							}

							break checkLoop;
						}
					}
				}

			} else if (uname.equals("quit")) {
				return "quit";
			} else {
				System.out.println("No such username exists, try again");
			}
		}

		System.out.println("Which account would you like to transfer too? enter 'quit' to quit");
		for (String s : accounts) {
			System.out.println("Account number: " + s);
		}
		String acc;
		checkLoop: while (true) {
			acc = scan.nextLine().toLowerCase();
			if (accounts.contains(acc)) {
				break checkLoop;
			} else if (acc.equals("quit")) {
				return "quit";
			} else {
				System.out.println("No such account exists, try again");
			}
		}
		return acc;

	}
}
