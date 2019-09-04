package com.banking.services;

import java.util.List;

import com.banking.dao.UserDao;
import com.banking.user.User;

public class UserServices {

	private static UserDao userD = new UserDao();

	public User get(int id) {
		return userD.get(id);
	}

	public List<User> getAll() {
		return userD.getAll();
	}

	public List<String> getAllAccounts(int id) {
		return userD.getAllAccounts(id);
	}

	public User getByUsername(String username) {
		return userD.getByUserName(username);
	}

	public void delete(User u) {
		userD.delete(u);
	}

	public void save(User u) {
		userD.save(u);
	}

}
