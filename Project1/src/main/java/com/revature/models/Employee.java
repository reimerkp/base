package com.revature.models;

public class Employee {

	private int eID;
	private String fName;
	private String lName;
	private String email;
	private String uname;
	private String pwd;
	private int imgId;

	public Employee() {
		super();
	}

	public Employee(int id, String uname) {
		this.eID = id;
		this.uname = uname;
	}

	public Employee(String fname, String lname, String email, String uname, String pwd, int imgId) {
		this.fName = fname;
		this.lName = lname;
		this.email = email;
		this.uname = uname;
		this.pwd = pwd;
		this.imgId = imgId;
	}

	public Employee(int eID, String fName, String lName, String email, String uname, String pwd, int imgId) {
		super();
		this.eID = eID;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.uname = uname;
		this.pwd = pwd;
		this.imgId = imgId;
	}

	public int geteID() {
		return eID;
	}

	public void seteID(int eID) {
		this.eID = eID;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getImgId() {
		return this.imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eID;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
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
		Employee other = (Employee) obj;
		if (eID != other.eID)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [eID=" + eID + ", fName=" + fName + ", lName=" + lName + ", email=" + email + ", uname="
				+ uname + ", pwd=" + pwd + "]";
	}

}
