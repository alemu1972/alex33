package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.JDBCUtility;

public class ReimbursementDAO {
	private static final int reimbursementId = 0;
	//private static final int reimb_id = 0;
	//private int reimbid;
	//private int reimbAuthorId;

	public List<Reimbursement> getAllReimbursements() throws SQLException{
		try(Connection con = JDBCUtility.getConnection()){
			List<Reimbursement> reimbursements = new ArrayList<>();
			String sql = " SELECT *FROM alemu_proj.ers_reimbursement";
				
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				int reimbAmount=rs.getInt("reimb_amount");
				String reimbSubmitted = rs.getString("reimb_submitted");
				String reimbResolved = rs.getString("reimb_resolved");
				String reimbStatus = rs.getString("reimb_status");
				String reimbType = rs.getString("reimb_type");
				String reimbDescription = rs.getString("reimb_description");
				int reimbAuthor =rs.getInt("reimb_author");
				int reimbResolver = rs.getInt("reimb_resolver");
				Reimbursement reimbursement = new Reimbursement(reimbId,reimbAmount,reimbSubmitted,reimbResolved,reimbStatus,reimbType,reimbDescription,reimbAuthor,reimbResolver);
				reimbursements.add(reimbursement);
				
			}
			return reimbursements;
		}
	}
	public List<Reimbursement> getReimbursementsByEmploye(int employeId) throws SQLException{
		try(Connection con = JDBCUtility.getConnection()){
			List<Reimbursement>reimbursements = new ArrayList<>();
String sql = "SELECT * FROM alemu_proj.ers_reimbursement WHERE reimb_author =?";
PreparedStatement pstmt = con.prepareStatement(sql);
pstmt.setInt(1, employeId);
ResultSet rs=pstmt.executeQuery();
while(rs.next()) {
	int reimbId1 = rs.getInt("reimb_id");
	int reimbAmount=rs.getInt("reimb_amount");
	String reimbSubmitted = rs.getString("reimb_submitted");
	String reimbResolved = rs.getString("reimb_resolved");
	String reimbStatus = rs.getString("reimb_status");
	String reimbType = rs.getString("reimb_type");
	String reimbDescription = rs.getString("reimb_description");
	
	int reimbAuthor =rs.getInt("reimb_author");
	int reimbResolver = rs.getInt("reimb_resolver");
	Reimbursement reimbursement = new Reimbursement(reimbId1,reimbAmount,reimbSubmitted,reimbResolved,reimbStatus,reimbType,reimbDescription,reimbAuthor,reimbResolver);
	reimbursements.add(reimbursement);
	
}
return reimbursements;


		}
		

	}
	public Reimbursement getReimbursementById(int id) throws SQLException {
		try (Connection c = JDBCUtility.getConnection()) {
			String sql = "SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_status, reimb_type, reimb_description, reimb_author, reimb_resolver "
					+ "FROM alemu_proj.ers_reimbursement "
					+ "WHERE reimb_id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id); // PASSES THE VALUE OF THE ID VARIABLE INTO THE FIRST QUESTION MARK

			ResultSet rs = pstmt.executeQuery(); //select, insert for rs

			 if (rs.next()) {
				 return new Reimbursement(rs.getInt("reimb_id"),
				rs.getInt("reimb_amount"),
				rs.getString("reimb_submitted"),
				rs.getString("reimb_resolved"),
				rs.getString("reimb_status"),
				rs.getString("reimb_type"),
				rs.getString("reimb_description"),
				rs.getInt("reimb_author"),
				rs.getInt("reimb_resolver"));
				
		}else {
			return null;
		}
	}catch(SQLException e) {
		throw new SQLException("Request cannot be completed");
	}
		
	}
	public Reimbursement updateReimbursement(int id,int reimbursementId, String reimbursementStatus) 
			 throws SQLException {
			try(Connection con = JDBCUtility.getConnection()) {
				
				String sql = "UPDATE alemu_proj.ers_reimbursements "
						+ "SET "
						+ "reimb_resolved = now(), "
						+ "reimb_status = ?, "
						+ "reimb_resolver = ?"
						+ "WHERE reimb_id = ?; ";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				
				//what time it was resolved
				//pstmt.setString(1, reimbursementResolved);
				//what's the status
				pstmt.setString(1, reimbursementStatus);
				//who resolved it .. resolver id and/or user id
				pstmt.setInt(2, id);
				//which reimbursement was changed
				pstmt.setInt(3, reimbursementId);
				
				pstmt.executeUpdate();
				Reimbursement s = getReimbursementById(reimbursementId); //
				
				//return new Reimbursement(id, reimbursementId, reimbursementStatus,); //matches in reimbModel
				return new Reimbursement(reimbursementId, s.getReimbAmount(), s.getReimbSubmitted(), 
						s.getReimbResolved(), reimbursementStatus, s.getReimbType(), 
						s.getReimbDescription(), s.getReimbAuthor(), id);
			}
			
		
		}



		/*public void updateStatus(int id, String status, int resolverId) throws SQLException {
			try (Connection con = JDBCUtility.getConnection()) {
				String sql = "UPDATE alemu_proj.ers_reimbursements "
						+ "SET "
						+ "resolved_time_stamp = date_trunc('second',now()::timestamp), "
						+ "status = ?, "
						+ "resolver_id = ? "
						+ "WHERE id = ?;";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, status);
				pstmt.setInt(2, resolverId);
				pstmt.setInt(3, id);
				
				int updatedCount = pstmt.executeUpdate();
				
				if (updatedCount != 1) {
					throw new SQLException("Something bad occurred when trying to update grade");
				}
			}
		
	}*/
		
	



				}

	



