package com.revature.models;

public class ReinbursementRequest {

	private int rID;
	private String reason;
	private double amount;
	private int eID;
	private int mID = -1;
	private boolean approved;

	public ReinbursementRequest() {
		super();
	}

	public ReinbursementRequest(String reason, double amount, int eID) {
		this.reason = reason;
		this.amount = amount;
		this.eID = eID;
	}

	public ReinbursementRequest(int rID, String reason, double amount, int eID) {
		this.rID = rID;
		this.reason = reason;
		this.amount = amount;
		this.eID = eID;
	}

	public ReinbursementRequest(int rID, String reason, double amount, int eID, int mID, boolean approved) {
		this.rID = rID;
		this.reason = reason;
		this.amount = amount;
		this.eID = eID;
		this.mID = mID;
		this.approved = approved;
	}

	public int getrID() {
		return rID;
	}

	public void setrID(int rID) {
		this.rID = rID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int geteID() {
		return eID;
	}

	public void seteID(int eID) {
		this.eID = eID;
	}

	public int getmID() {
		return mID;
	}

	public void setmID(int mID) {
		this.mID = mID;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + eID;
		result = prime * result + mID;
		result = prime * result + rID;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		ReinbursementRequest other = (ReinbursementRequest) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (approved != other.approved)
			return false;
		if (eID != other.eID)
			return false;
		if (mID != other.mID)
			return false;
		if (rID != other.rID)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "reinbursementRequest [rID=" + rID + ", reason=" + reason + ", amount=" + amount + ", eID=" + eID
				+ ", mID=" + mID + ", approved=" + approved + "]";
	}

}
