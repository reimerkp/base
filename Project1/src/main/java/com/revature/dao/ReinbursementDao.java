package com.revature.dao;

import java.util.List;

import com.revature.models.ReinbursementRequest;

public interface ReinbursementDao {

	public List<ReinbursementRequest> getAll();

	public ReinbursementRequest getById(int id);

	public List<ReinbursementRequest> getAllByEid(int eID);

	public int setManagerApproval(int rId, int mID, boolean approved);

	public int createReinbursementRequest(ReinbursementRequest r);

	public int updateReinbursementRequest(ReinbursementRequest old, ReinbursementRequest newR);

	public int deleteReinbursementRequest(ReinbursementRequest r);

}
