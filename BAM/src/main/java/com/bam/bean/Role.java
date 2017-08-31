package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/**
 * 
 * @author Duncan Hayward
 * This is going to reference the lookup table for a Role, 
 * Not currently being used but needed to secure rest end points in Spring Security
 *
 */
@Entity
@Table(name="USER_ROLE")
@Component
public class Role {

	@Id
	@Column(name = "ROLE_ID")
	private int roleId;
	
	@Column(name= "USER_ROLE")
	private String userRole;


	public Role() {
		super();
	}


	public Role(int roleId, String userRole) {
		super();
		this.roleId = roleId;
		this.userRole = userRole;
	}


	public int getUserId() {
		return roleId;
	}


	public void setUserId(int userId) {
		this.roleId = userId;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", userRole=" + userRole + "]";
	}
	
	
}
