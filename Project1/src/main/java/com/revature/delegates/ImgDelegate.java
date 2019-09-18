package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Image;
import com.revature.services.ImageService;

public class ImgDelegate {

	private ImageService is = new ImageService();

	public void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String idStr = request.getParameter("id");

		if (idStr != null) {
			int id = Integer.parseInt(idStr);
			Image i = is.getImage(id);
			if (i != null) {
				try (PrintWriter pw = response.getWriter()) {
					pw.write(new ObjectMapper().writeValueAsString(i));
				}
			}
		} else {
			response.sendError(404, "No Image ID Given");
		}
	}

}
