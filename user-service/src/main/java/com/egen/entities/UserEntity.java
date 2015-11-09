package com.egen.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserEntity {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private String id;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "middleName", nullable = true)
	private String middleName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "age", nullable = false)
	private int age;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "zip", nullable = true)
	private String zip;

	public UserEntity() {

	}

	public UserEntity(String id, String firstName, String middleName, String lastName, int age, String gender,
			String phone, String zip) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.zip = zip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
