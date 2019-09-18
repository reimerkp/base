package com.revature.services;

import java.util.List;

import com.revature.dao.ReinbursementDaoImpl;
import com.revature.models.ReinbursementRequest;

public class ReinbursementService {

	ReinbursementDaoImpl rDao = new ReinbursementDaoImpl();

	public List<ReinbursementRequest> getAll() {
		return rDao.getAll();
	}

	public List<ReinbursementRequest> getAllByEid(int eID) {
		if (eID > 0) {
			return rDao.getAllByEid(eID);
		} else
			return null;
	}

	public ReinbursementRequest getById(int id) {
		if (id > 0)
			return rDao.getById(id);
		else
			return null;
	}

	public int setManagerApproval(int rId, int mID, boolean approved) {
		int count = -3;
		if (rId > 0) {
			count++;
			if (mID > 0) {
				count = rDao.setManagerApproval(rId, mID, approved);
			}
		}
		return count;
	}

	public int createReinbursementRequest(ReinbursementRequest r) {
		int count = -1;
		if (r != null) {
			count = rDao.createReinbursementRequest(r);
		}
		return count;
	}

	public int updateReinbursementRequest(ReinbursementRequest old, ReinbursementRequest newR) {
		int count = -2;
		if (newR.getAmount() > 0) {
			count++;
			if (newR.getReason() != null) {
				count = rDao.updateReinbursementRequest(old, newR);
			}
		}
		return count;
	}

	public int deleteReinbursementRequest(ReinbursementRequest r) {
		int count = -1;
		if (r != null) {
			count = rDao.deleteReinbursementRequest(r);
		}
		return count;
	}
}
