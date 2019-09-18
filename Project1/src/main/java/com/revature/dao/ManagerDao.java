package com.revature.dao;

import java.util.List;

import com.revature.models.Manager;

public interface ManagerDao {
	public List<Manager> getAll();

	public Manager getById(int id);

	public int updateDept(Manager m, String dept);

	public int createManager(Manager m);

	public int deleteManager(Manager m);
}
