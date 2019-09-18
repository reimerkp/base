package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.delegates.AuthDelegate;
import com.revature.delegates.EmployeeDelegate;
import com.revature.delegates.ImgDelegate;
import com.revature.delegates.ReinbursementDelegate;
import com.revature.delegates.ViewDelegate;

public class RequestHelper {

	private AuthDelegate authDelegate = new AuthDelegate();
	private EmployeeDelegate emplDelegate = new EmployeeDelegate();
	private ViewDelegate viewDelegate = new ViewDelegate();
	private ImgDelegate imgDelegate = new ImgDelegate();
	private ReinbursementDelegate rDel = new ReinbursementDelegate();

	public void processGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String path = request.getServletPath();
		if (path.startsWith("/api/")) {

			// we will authenticate the token here
			if (!authDelegate.isAuthorized(request)) {
				response.sendError(401);
				return;
			}
			System.out.println("Made it past isAuthorized");
			String record = path.substring(5);
			System.out.println(record);
			switch (record) {
			case "employees":
				emplDelegate.getUsers(request, response);
				break;
			case "img":
				imgDelegate.getImage(request, response);
				break;
			case "requests":
				rDel.getRequests(request, response);
				break;
			default:
				response.sendError(404, "Request Record(s) Not Found");
			}

		} else {
			viewDelegate.returnView(request, response);
		}

	}

	public void processPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getServletPath();
		System.out.println(path);
		switch (path) {
		case "/login":
			authDelegate.authenticate(request, response);
			break;
		case "/update":
			emplDelegate.updateUser(request, response);
			break;
		case "/reinburse":
			rDel.createNewReinbursement(request, response);
			break;
		case "/updateReq":
			rDel.updateManagerApproval(request, response);
			break;
		default:

			response.sendError(405);
		}
	}
}
