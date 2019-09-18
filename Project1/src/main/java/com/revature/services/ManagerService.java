package com.revature.services;

import java.util.List;

import com.revature.dao.ManagerDaoImpl;
import com.revature.models.Manager;

public class ManagerService {
	private ManagerDaoImpl mDao = new ManagerDaoImpl();

	public List<Manager> getAll() {
		return mDao.getAll();
	}

	public Manager getById(int id) {
		Manager m = null;
		if (id > 0) {
			m = mDao.getById(id);
		}
		return m;
	}

	public int updateDept(Manager m, String dept) {
		int count = -2;
		if (m != null) {
			count++;
			if (dept != null) {
				count = mDao.updateDept(m, dept);
			}
		}
		return count;
	}

	public int createManager(Manager m) {
		int count = -1;
		if (m != null) {
			count = mDao.createManager(m);
		}
		return count;
	}

	public int deleteManager(Manager m) {
		int count = -1;
		if (m != null) {
			count = mDao.deleteManager(m);
		}
		return count;
	}
}
