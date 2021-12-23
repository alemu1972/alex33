package com.revature.service;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;

public class AuthorizationService {
	public void authorizeEmployeAndFinanceManager(User user) throws UnauthorizedException {
		if(user==null||!(user.getUserRole().equals("employe")||user.getUserRole().equals("Finance Manager"))) {
			
			throw new UnauthorizedException( "You  must be logged in and have an employe or finance manager role to accessing this document");
		}
	}
	public void authorizeFinanceManager(User user) throws UnauthorizedException {
		if(user==null|| !user.getUserRole().equals("Finance Manager")) {
			throw new UnauthorizedException("You must be logged in and  have a finance manager role to access this data");
		}
	}
	public void authorizeEmployee(User currentLoggedIn) {
		
		
	}

}
