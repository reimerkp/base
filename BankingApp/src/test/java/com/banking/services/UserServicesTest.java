package com.banking.services;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

import com.banking.encrypt.Encrypter;
import com.banking.user.User;

public class UserServicesTest {

	private UserServices uS = new UserServices();
	private Encrypter encrypt;

	@Test
	public void testGetUserByID() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		encrypt = new Encrypter();
		User a = uS.get(3);
		User actual = new User(a.getId(), a.getUsername(), encrypt.decrypt(a.getPassword()));
		User expected = new User(3, "user2", "pass2");
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAllUsers() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		encrypt = new Encrypter();
		List<User> userList = uS.getAll();
		List<User> actual = new ArrayList<User>();
		for (User u : userList) {
			User newU = new User(u.getId(), u.getUsername(), encrypt.decrypt(u.getPassword()));
			actual.add(newU);
		}
		User u1 = new User(2, "user1", "pass1");
		User u2 = new User(3, "user2", "pass2");
		User u3 = new User(4, "big8852", "P@sswor$1");
		User u4 = new User(5, "admin", "adminPass1");

		List<User> expected = new ArrayList<User>();
		expected.add(u1);
		expected.add(u2);
		expected.add(u3);
		expected.add(u4);

		assertEquals(expected, actual);

	}
	
	
	
}
