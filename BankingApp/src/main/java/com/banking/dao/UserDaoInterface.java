package com.banking.dao;

import com.banking.user.User;
import java.util.List;

public interface UserDaoInterface {

	User get(int id);

	User getByUserName(String uname);

	List<User> getAll();

	List<String> getAllAccounts(int id);
	
	boolean save(User u);

	boolean update(User u, String[] params);

	boolean delete(User u);
}
