package com.revature.models;

public class Manager {

	private int mID;
	private String dept;
	
	public Manager() {
		super();
	}
	public Manager(int mID, String dept) {
		super();
		this.mID = mID;
		this.dept = dept;
	}
	public int getmID() {
		return mID;
	}
	public void setmID(int mID) {
		this.mID = mID;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + mID;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (mID != other.mID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Manager [mID=" + mID + ", dept=" + dept + "]";
	}
	
	
}
