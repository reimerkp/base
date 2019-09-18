package com.revature.delegates;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.Employee;
import com.revature.models.Manager;
import com.revature.services.EmployeeService;
import com.revature.services.ManagerService;

public class AuthDelegate {

	// private UserDao userDao = new UserDaoImpl();
	private EmployeeService eS = new EmployeeService();
	private ManagerService mS = new ManagerService();

	public boolean isAuthorized(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		System.out.println("AuthToken: " + authToken);
		// check to see if there is an auth header
		if (authToken != null) {
			String[] tokenArr = authToken.split(":");
			// if the token is formatted the way we expect, we can take the id from it and
			// query for that user
			if (tokenArr.length == 3 || tokenArr.length == 4) {
				String idStr = tokenArr[0];
				String userName = tokenArr[1];
				if (idStr.matches("^\\d+$")) {
					// check to see if there is a valid user and if that user is the appropriate
					// role in their token
					Employee e = eS.authEmpl(Integer.parseInt(idStr));
					if (e != null && e.getUname().equals(userName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Employee e = eS.getByUsernameAndPassword(username, password);
		if (e != null) {
			String token = "";
			Manager m = mS.getById(e.geteID());
			if (m != null) {
				token = m.getmID() + ":" + e.getUname() + ":" + e.getImgId() + ":" + "m";
			} else {
				token = e.geteID() + ":" + e.getUname() + ":" + e.getImgId();
			}
			System.out.println(token);
			response.setStatus(200);
			response.setHeader("Authorization", token);

		} else {
			response.sendError(401);
		}

	}
}
