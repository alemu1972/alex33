package com.revature.controller;

import com.revature.model.User;
import com.revature.service.AuthorizationService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class EmployeController implements Controller {
	private AuthorizationService authorizationService;
	public EmployeController() {
		this.authorizationService = new AuthorizationService();
	}
private Handler getEmployeById = (ctx)->{
	User user = (User) ctx.req.getSession().getAttribute("currentuser");
	this.authorizationService.authorizeEmployeAndFinanceManager(user);
	ctx.result("I WAS ABLE TO ACCESS THE GET EMPLOYE BY ID ENDPOINT");
};
private Handler addEmploye = (ctx)->{
	User user = (User) ctx.req.getSession().getAttribute("currentuser");
	this.authorizationService.authorizeFinanceManager(user);
	ctx.result("I WAS ABLE TO ACCESS THE ADD EMPLOYE ENDPOINT");
};
@Override
public void mapEndPoints(Javalin app) {
	app.get("/employe/{emloyeId}",getEmployeById);
	app.post("/employes", addEmploye);
	
	
}
}
