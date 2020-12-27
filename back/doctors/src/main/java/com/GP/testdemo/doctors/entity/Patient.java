package com.GP.testdemo.doctors.entity;


import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "patient")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Patient implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="midle_name")
	private String midletName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="SSID")
	private String SSID;

	@Column(name="image")
	private String image;
	

	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id",referencedColumnName = "id")
	private PatientAddress address;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="sign_id",referencedColumnName = "id")
	private PatientSign sign;

	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "doctor",cascade = {CascadeType.DETACH,CascadeType.MERGE ,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Appointment> appointments;
	
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public void add(Appointment tempAppointment) {
		
		if (appointments == null) {
			appointments = new ArrayList<Appointment>();					
		}
		
		appointments.add(tempAppointment);
		
		tempAppointment.setPatient(this);
	}
	
	public Patient() {
		
	}
	
	public Patient( String firstName, String midletName, String lastName, String SSID,String image) {
		this.firstName = firstName;
		this.midletName = midletName;
		this.lastName = lastName;
		this.SSID = SSID;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMidletName() {
		return midletName;
	}

	public void setMidletName(String midletName) {
		this.midletName = midletName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public PatientAddress getAddress() {
		return address;
	}

	public void setAddress(PatientAddress address) {
		this.address = address;
	}

	public PatientSign getSign() {
		return sign;
	}

	public void setSign(PatientSign sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "Patien [id=" + id + ", firstName=" + firstName + ", midletName=" + midletName + ", lastName=" + lastName
				+ ", SSID=" + SSID + ", college="  + ", address=" + address.toString() + ", sign=" + sign.toString() + "]";
	}

		
}
