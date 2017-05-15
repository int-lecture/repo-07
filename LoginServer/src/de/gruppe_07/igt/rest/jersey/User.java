package de.gruppe_07.igt.rest.jersey;

import java.util.Date;

public class User {

	private String name;
	private String pseudonym;
	private String password;
	private String token;
	private Date expireDate;
	
	public User(String name, String pseudonym, String password, String token, Date expireDate){
		this.name = name;
		this.setPseudonym(pseudonym);
		this.password = password;
		this.token = token;
		this.expireDate = expireDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPseudonym() {
		return pseudonym;
	}
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}
