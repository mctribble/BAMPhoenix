package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("User Role")
public class Role {

	@Id
	@Column(name = "ROLE_ID")
	@ApiModelProperty(notes = "User role id")
	private int roleId;
	
	@Column(name= "USER_ROLE")
	@ApiModelProperty(notes = "Role name")
	private String userRole;


	public Role() {
		super();
	}


	public Role(int roleId, String userRole) {
		super();
		this.roleId = roleId;
		this.userRole = userRole;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int userId) {
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
