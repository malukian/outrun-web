package edu.fra.uas.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import edu.fra.uas.common.BaseEntity;
//representing a User entity in a security module of a Java application
@Entity
@Table(name = "secuser")
public class User extends BaseEntity<Long> {

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	public Long getId() {
		return super.getId();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + getId() + ", name=" + nickname + ", email=" + email.replaceFirst("@.*", "@***")
				+ ", password='" + password.substring(0, 10) + '}';
	}

}
