package com.dudlo.reservationsystem.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "USER_TABLE")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userID;
		
	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String userName;
	
	@JsonIgnore
	@Column
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List <Reservation> reservations;
	
	@JsonIgnore
	@Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
	
	
	protected User() {	}
	
	public User(String firstName, String lastName, String userName, String password, RoleEnum role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName= userName;
		this.password = password;
		this.reservations = new ArrayList<Reservation>();
		this.role = role;
	}

	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}
	/**
	 * @return the reservations
	 */
	public List<Reservation> getReservations() {
		return reservations;
	}

	
	/**
	 * @return the userID
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the role
	 */
	public RoleEnum getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(RoleEnum role) {
		this.role = role;
	}

	

}
