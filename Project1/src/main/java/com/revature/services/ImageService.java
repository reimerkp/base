package com.revature.services;

import java.io.InputStream;

import com.revature.dao.ImgDaoImpl;
import com.revature.models.Image;

public class ImageService {

	private ImgDaoImpl imgD = new ImgDaoImpl();

	public int InsertImgFile(int id, InputStream f, String name) {
		return imgD.InsertImgFile(id,f, name);
	}

	public int updateImgFile(int oldId, InputStream f, String fName) {
		if (oldId != 0 && f != null && fName != null) {
			int check = imgD.updateImg(oldId, f, fName);
			if (check == 1) {
				return oldId;
			}
			return 0;
		} else
			return 0;
	}

	public byte[] getImageById(int id) {
		if (id > 0) {
			return imgD.getImgBytes(id);
		}
		return null;
	}

	public Image getImage(int id) {
		if (id > 0) {
			return imgD.getImageById(id);
		}
		return null;
	}
}
