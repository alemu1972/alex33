package com.revature.service;


import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.revature.dao.ReimbursementDAO;
import com.revature.exception.ReimbursementAlreadyResolvedException;
import com.revature.exception.ReimbursementAlreadyUpdatedException;
import com.revature.exception.ReimbursementNotFoundException;

import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {
	private ReimbursementDAO reimbursementDao;
	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}
	public ReimbursementService(ReimbursementDAO reimbursementDao) {
		this.reimbursementDao = reimbursementDao;
	}
	public List<Reimbursement> getReimbursements(User currentlyLoggedInUser) throws SQLException{
	List<Reimbursement>reimbursements = null;
	if(currentlyLoggedInUser.getUserRole().equals("Finance Manager")) {
		reimbursements = this.reimbursementDao.getAllReimbursements();
	}else if(currentlyLoggedInUser.getUserRole().equals("employe")) {
	reimbursements = this.reimbursementDao.getReimbursementsByEmploye(currentlyLoggedInUser.getId());
	}
	
	return reimbursements;	
	}

	public Reimbursement getReimbursementById(User currentLoggedIn, String reimbId) throws SQLException, ReimbursementNotFoundException {
 

			try {
				int id = Integer.parseInt(reimbId);

				Reimbursement r = this.reimbursementDao.getReimbursementById(id);

				if (r == null) {
					throw new ReimbursementNotFoundException("Reimbursement with an id of " + id + " was not found");
				}
				return r;
			} catch (NumberFormatException e) {
				throw new InvalidParameterException("Id provided is not an int convertable value");
			}
	}
		
	
	public Reimbursement updateReimbursement(User currentlyLoggedInUser, String reimbursementId, String status)
			throws SQLException, ReimbursementAlreadyUpdatedException, ReimbursementAlreadyResolvedException,
			InvalidParameterException, ReimbursementNotFoundException {
		try {
			int id = Integer.parseInt(reimbursementId);
			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);

			if (reimbursement == null) {
				throw new ReimbursementNotFoundException("Reimbursement with id of" + reimbursementId + "was not found");
			}

			if (reimbursement.getReimbResolver() == 0) {
				this.reimbursementDao.updateReimbursement(id, id, status);
			} else {
				throw new ReimbursementAlreadyResolvedException("Reimbursement is already paid");
			}

			return this.reimbursementDao.getReimbursementById(id);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id provided is not an int");
		}
	}
}
	
		/*try {
			//use hashset= every item is unique


			Set<String> reimbursementStatus = new HashSet<>();
		
			reimbursementStatus.add("Pending");
			reimbursementStatus.add("Approved");
			reimbursementStatus.add("Denied");
			if (!(reimbursementStatus.contains(Status))) {
				throw new InvalidParameterException("Reimbursement cannot be null! Must have a status of pending, approved, or denied.");
			}
			
			int id = Integer.parseInt(reimbursementId);
			//int resolver = Integer.parseInt(reimbursementResolver);
			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);

			if(reimbursement == null) { //check is reimbursement exists or not
				throw new ReimbursementNotFoundException ("Reimbursement with an id of " + id + " was not found");
			}
			if(reimbursement.getReimbResolver() != 0) { //if !=0, reimb alr resolved
				throw new ReimbursementAlreadyResolvedException("Reimbursement has already been resolved");
			}else { //==0 
				return (
                  this.reimbursementDao).updateReimbursement (  currentLoggedIn.getId(), id, Status);
			}
			}catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id MUST be an int");
		
			
		}
	}}	*/
 


	
	