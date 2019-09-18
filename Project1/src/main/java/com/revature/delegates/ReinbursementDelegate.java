package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ReinbursementRequest;
import com.revature.services.ReinbursementService;

public class ReinbursementDelegate {

	ReinbursementService rs = new ReinbursementService();

	public void updateManagerApproval(HttpServletRequest request, HttpServletResponse response) {
		String rId = request.getParameter("rId");
		String mId = request.getParameter("mId");
		String status = request.getParameter("status");
		boolean stat = false;
		if (status.equals("true")) {
			stat = true;
		}
		rs.setManagerApproval(Integer.parseInt(rId), Integer.parseInt(mId), stat);
		response.setStatus(202);
	}

	public void createNewReinbursement(HttpServletRequest request, HttpServletResponse response) {
		String reason = request.getParameter("reason");
		String amt = request.getParameter("amount");
		String eId = request.getParameter("eId");
		ReinbursementRequest rR = new ReinbursementRequest(reason, Double.parseDouble(amt), Integer.parseInt(eId));
		System.out.println(rs.createReinbursementRequest(rR));
		response.setStatus(202);
	}

	public void getRequests(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String eId = request.getParameter("eId");
		if (eId == null) {
			List<ReinbursementRequest> reinbursements = rs.getAll();
			try (PrintWriter pw = response.getWriter()) {
				pw.write(new ObjectMapper().writeValueAsString(reinbursements));
			}
		} else {
			List<ReinbursementRequest> reinbursements = rs.getAllByEid(Integer.parseInt(eId));
			if (reinbursements != null) {
				try (PrintWriter pw = response.getWriter()) {
					pw.write(new ObjectMapper().writeValueAsString(reinbursements));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
