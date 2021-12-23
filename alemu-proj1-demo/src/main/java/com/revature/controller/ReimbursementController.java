package com.revature.controller;


import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller {
	private static final String reimbId = null;
	private AuthorizationService authService;
	private ReimbursementService reimbursementService;
	private User currentlyLoggedInUser;
	public ReimbursementController() {
		this.authService = new AuthorizationService();
		this.reimbursementService = new ReimbursementService();
	}
	private Handler getReimbursements =(ctx)->{
		
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeAndFinanceManager(currentlyLoggedInUser);
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursements(currentlyLoggedInUser);
		ctx.json(reimbursements);
	};
	private Handler getReimbursementByEmploye =(ctx)->{
		User currentlyLoggedInUser =(User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeAndFinanceManager(currentlyLoggedInUser);
		String reimbursementAuthor = ctx.pathParam("reimb_author");
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursements(currentlyLoggedInUser);
		ctx.json(reimbursements);
	
	};
	private Handler getReimbursementById = (ctx) -> {
		User currentLoggedIn = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinanceManager(currentLoggedIn);
		
		String reimbursementId = ctx.pathParam("reimb_id");
		Reimbursement reimbursement = this.reimbursementService.getReimbursementById(currentLoggedIn, reimbId);
		
		ctx.json(reimbursement);
	};
	private Handler updateReimbursement = (ctx) -> {
		User currentLoggedIn = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinanceManager(currentLoggedIn);

		String reimbursementId = ctx.pathParam("reimb_id");
		String reimbursementStatus = ctx.formParam("reimbursementStatus");
		String reimbursementResolved = ctx.formParam("reimbursementResolved");
	//String reimbursementResolver = ctx.pathParam("reimbursement resolver");
		System.out.println(reimbursementResolved);
		Reimbursement reimbursement = this.reimbursementService.updateReimbursement( currentLoggedIn, reimbursementId,
				reimbursementStatus);
		
		ctx.json(reimbursement);

		
	};
	private Handler updateStatus;
/*private Handler addReimbursement = (ctx) -> {
		
		User currentLoggedIn = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployee(currentLoggedIn);
		
		String reimbursementType = ctx.formParam("reimbursementType");
		String reimbursementAmount   = ctx.formParam("reimbursementAmount");
		String reimbursementDescription = ctx.formParam("reimbursementDescription");
		
		//gets files from HTTP request
		//UploadedFile file = ctx.uploadedFile("reimbursementReceipt");
		if (file == null) {
			ctx.status(400); //bad response, server cannot process
			ctx.json(new MessageDTO("You must upload a receipt- ONLY JPEG, or PNG"));
			return;
		}
		
		InputStream content = file.getContent(); //actual content of file
		
		
		Tika tika = new Tika();
		//disallow files that aren't jpeg,or png
		//image/jpeg
		//image/png
		String mimeType = tika.detect(content); //detects mimetype
		System.out.println(mimeType);
		//service layer
		Reimbursement addedReimbursement = this.reimbursementService.addReimbursement(currentLoggedIn, mimeType, reimbursementType, 
			reimbursementAmount, reimbursementDescription, content);
		ctx.json(addedReimbursement);
		ctx.status(201); //content created
 	};*/



	@Override
	public void mapEndPoints(Javalin app) {
		app.get("/reimbursements", getReimbursements);

		//app.patch("/reimbursements/{reimb_id}/status", updateStatus);
	   // app.post("/reimbursements", addReimbursement);
		app.put("/reimbursements/{reimb_id}/status", updateReimbursement);
	}
	

}

