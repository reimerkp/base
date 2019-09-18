package com.revature.dao;

import java.io.InputStream;

import com.revature.models.Image;

public interface ImgDao {

	public int InsertImgFile(int id,InputStream f, String name);

	public byte[] getImgBytes(int id);

	public int updateImg(int id, InputStream f, String fName);

	public int deleteImg(int id);

	public Image getImageById(int id);
}
