package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.User;
import com.revature.service.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller {
	private UserService userService;
	public AuthenticationController() {
		this.userService = new UserService();
	}
	private Handler login =(ctx)->{
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
		User user=this.userService.getUserByUsernameAndPassword(loginDTO.getUsername(),loginDTO.getPassword());
		HttpServletRequest req = ctx.req;
		if(req.getSession(false)!=null) {
	    ctx.json(new MessageDTO("You are already logged in"));
		}else {
	HttpSession session =req.getSession();
	session.setAttribute("currentuser", user);
	ctx.json(user);	
		}
	};
	private Handler logout = (ctx)->{
		HttpServletRequest req= ctx.req;
if(req.getSession(false)==null||req.getSession(false).getAttribute("currentuser")==null) {
	ctx.json(new MessageDTO("You are not logged in"));
}else {
	User user =(User) req.getSession(false).getAttribute("currentuser");
	req.getSession(false).invalidate();
	ctx.json(new MessageDTO(user + "is now logged out"));
}
		
	};
	private Handler CheckIfLoggedIn = (ctx)->{
	HttpSession session = ctx.req.getSession();	
	if(!(session.getAttribute("currentuser")==null)) {
		ctx.json(session.getAttribute("currentuser"));
		ctx.status(200);
	}else {
		ctx.json(new MessageDTO("User is not logged in"));
		ctx.status(401);

	}
	};
	@Override
	public void mapEndPoints(Javalin app) {
       app.post("/login", login);
       app.post("logout", logout);
		app.get("/checkloginstatus", CheckIfLoggedIn);
	}
	

}
