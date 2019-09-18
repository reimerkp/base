package com.revature.services;

import java.util.List;

import com.revature.dao.EmployeeDaoImpl;
import com.revature.models.Employee;

public class EmployeeService {

	private EmployeeDaoImpl eDao = new EmployeeDaoImpl();

	public List<Employee> getAll() {
		return eDao.getAll();
	}

	public List<String> getAllUsernames() {
		return eDao.getAllUsernames();
	}

	public Employee getByUsernameAndPassword(String uname, String pwd) {
		Employee e = null;
		if (null != uname && null != pwd) {
			e = eDao.getByUsernameAndPassword(uname, pwd);
		}
		return e;
	}

	public Employee getById(int id) {
		Employee e = null;
		if (id != 0) {
			e = eDao.getById(id);
		}
		return e;
	}

	public Employee authEmpl(int id) {
		return eDao.authEmpl(id);
	}

	public int createEmployee(Employee e) {
		int check = 0;
		if (e != null) {
			check = eDao.createEmployee(e);
		}
		return check;
	}

	public int updateEmployee(int oldID, Employee newE) {
		int check = 0;
		if (oldID > 0) {
			check = 2;
			if (newE != null) {
				check = eDao.updateEmployee(oldID, newE);
			}
		}
		return check;
	}

	public int deleteEmployee(Employee e) {
		int check = 0;
		if (e != null) {
			check = eDao.deleteEmployee(e);
		}
		return check;
	}

}
