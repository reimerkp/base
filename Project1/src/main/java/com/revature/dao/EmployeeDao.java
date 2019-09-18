package com.revature.dao;

import java.util.List;

import com.revature.models.Employee;

public interface EmployeeDao {

	public List<Employee> getAll();

	public List<String> getAllUsernames();

	public Employee getByUsernameAndPassword(String uname, String pwd);

	public Employee getById(int id);

	public int createEmployee(Employee e);

	public int updateEmployee(int oldID, Employee newE);

	public int deleteEmployee(Employee e);

	public Employee authEmpl(int id);

}
