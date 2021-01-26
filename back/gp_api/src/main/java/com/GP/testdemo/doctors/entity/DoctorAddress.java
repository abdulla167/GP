//package com.GP.testdemo.doctors.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="doctor_address")
//public class DoctorAddress implements Serializable{
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private int id;
//
//	@Column(name="country")
//	private String country;
//
//	@Column(name="city")
//	private String city;
//
//	@Column(name="address")
//	private String address;
//
//
//	public DoctorAddress() {
//
//	}
//
//	public DoctorAddress(String country, String city, String address) {
//		this.country = country;
//		this.city = city;
//		this.address = address;
//
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
////	public Doctor getDoctor() {
////		return doctor;
////	}
////
////	public void setDoctor(Doctor doctor) {
////		this.doctor = doctor;
////	}
//
//	@Override
//	public String toString() {
//		return "DoctorAddress [id=" + id + ", country=" + country + ", city=" + city + ", address=" + address
//			;
//	}
//
//}
