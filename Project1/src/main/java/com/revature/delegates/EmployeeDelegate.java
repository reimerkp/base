package com.revature.delegates;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Employee;
import com.revature.models.Image;
import com.revature.services.EmployeeService;
import com.revature.services.ImageService;

public class EmployeeDelegate {

	private ImageService is = new ImageService();
	private EmployeeService es = new EmployeeService();

	public void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		if (idStr == null) {
			List<Employee> employees = es.getAll();
			try (PrintWriter pw = response.getWriter()) {
				pw.write(new ObjectMapper().writeValueAsString(employees));
			}
		} else {
			if (idStr.matches("^\\d+$")) {
				Employee e = es.getById(Integer.parseInt(idStr));
				if (e == null) {
					response.sendError(404, "No Employee with given ID");
				} else {
					try (PrintWriter pw = response.getWriter()) {
						pw.write(new ObjectMapper().writeValueAsString(e));
					}
				}
			} else {
				response.sendError(400, "Invalid ID param");
			}
		}
	}

	public void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		int imgId = -1;
		InputStream content = null;
		String fileName = null;
		ArrayList<String> empArgs = new ArrayList<String>();
		try {

			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

			for (FileItem item : items) {

				if (item.isFormField()) {
					empArgs.add(item.getString());
				} else {
					fileName = item.getName();

					content = item.getInputStream();
				}

			}

		} catch (org.apache.commons.fileupload.FileUploadException e) {

			throw new ServletException("Parsing file upload failed.", e);

		}
		for (String s : empArgs) {
			System.out.println(s);
		}
		String fName = empArgs.get(0);
		String lName = empArgs.get(1);
		String email = empArgs.get(2);
		String pwd = empArgs.get(3);
		int eId = Integer.parseInt(empArgs.get(4));
		Employee old = es.getById(eId);
		if (content != null) {
			if (old.getImgId() != 0) {
				imgId = is.updateImgFile(old.getImgId(), content, fileName);
			} else
				imgId = is.InsertImgFile(eId, content, fileName);
		} else {
			imgId = old.getImgId();
		}
		System.out.println("Image id: " + imgId);
		Employee e;
		if (pwd.length() == 0) {
			e = new Employee(fName, lName, email, "def", old.getPwd(), imgId);
		} else {
			e = new Employee(fName, lName, email, "def", pwd, imgId);
		}
		int check = es.updateEmployee(eId, e);
		System.out.println(check);
		response.setStatus(202);
		if (imgId != 0) {
			String img = "" + imgId;
			try (PrintWriter pw = response.getWriter()) {
				pw.write(img);
				
			}
		}

	}
}
