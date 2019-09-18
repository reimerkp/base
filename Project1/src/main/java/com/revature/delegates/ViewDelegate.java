package com.revature.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDelegate {

	public void returnView(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getServletPath();
		// System.out.println("Path in ReturnView: " + path);
		switch (path) {
		case "/login":
			request.getRequestDispatcher("/static/HTML/index.html").forward(request, response);
			break;
		case "/home":
			request.getRequestDispatcher("/static/HTML/employeeHomepage.html").forward(request, response);
			break;
		case "/profile":
			request.getRequestDispatcher("/static/HTML/Profile.html").forward(request, response);
			break;
		case "/editProf":
			request.getRequestDispatcher("/static/HTML/editProf.html").forward(request, response);
			break;
		case "/requestReinb":
			request.getRequestDispatcher("/static/HTML/requestReinbursement.html").forward(request, response);
			break;
		case "/getAllEmployees":
			request.getRequestDispatcher("/static/HTML/allEmployees.html").forward(request, response);
			break;
		case "/getAllRequests":
			request.getRequestDispatcher("/static/HTML/allRequests.html").forward(request, response);
			break;
		default:
			response.sendError(404, "static resource not found");
		}
	}
}
