package com.metafour.mtrak.router.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author noor
 *
 */
@Configuration
@ConfigurationProperties(prefix = "mrouter")
public class MrouterConfig {
	private String userRoleAdmin = "ADMIN";
	private String userRoleSuperAdmin = "SADMIN";
	private List<User> users = new ArrayList<User>();

	public static class User {
		private String code;
		private String password;
		private String[] roles;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String[] getRoles() {
			return roles;
		}

		public void setRoles(String[] roles) {
			this.roles = roles;
		}
	}

	public String getUserRoleAdmin() {
		return userRoleAdmin;
	}

	public void setUserRoleAdmin(String userRoleAdmin) {
		this.userRoleAdmin = userRoleAdmin;
	}

	public String getUserRoleSuperAdmin() {
		return userRoleSuperAdmin;
	}

	public void setUserRoleSuperAdmin(String userRoleSuperAdmin) {
		this.userRoleSuperAdmin = userRoleSuperAdmin;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
