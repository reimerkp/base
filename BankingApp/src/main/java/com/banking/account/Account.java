package com.banking.account;

public class Account {

	private String type;
	private String accountNumber;
	private float amount;

	// create new AccountDAO
	public Account(String accountNo, String accType) {
		type = accType;
		accountNumber = accountNo;
		amount = 0;

	}

	public Account(String accountNo, String accType, float money) {
		type = accType;
		accountNumber = accountNo;
		amount = money;
	}

	public boolean addMoney(float amount) {
		if (amount < 0) {
			return false;
		}
		this.amount += amount;
		// add money through AccountDAO object
		return true;
	}

	public boolean withdrawMoney(float amount) {
		if (amount < 0)
			return false;
		else if (amount > this.amount)
			return false;
		else {
			this.amount -= amount;
			return true;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Account Number=" + accountNumber + ", Account Name = " + type + ", amount=" + amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	

}
