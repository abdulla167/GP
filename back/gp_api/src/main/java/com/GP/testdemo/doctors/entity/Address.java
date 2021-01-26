package com.GP.testdemo.doctors.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="address")
public class Address implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="country")
	private String country;

	@Column(name="city")
	private String city;

	@Column(name="address")
	private String address;


	public Address() {

	}

	public Address(String country, String city, String address) {
		this.country = country;
		this.city = city;
		this.address = address;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Address [id=" + id + ", country=" + country + ", city=" + city + ", address=" + address
			;
	}
	
}
