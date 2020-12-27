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
@Table(name = "doctor")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Doctor implements Serializable{

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

	@Column(name="fees")
	private int fees;
	
	@Column(name="SSID")
	private String SSID;
	
	@Column(name="college")
	private String college;

	@Column(name="graduation_year")
	private Date graduationYear;
	
	@Column(name="specialization")
	private String specialization;
	
	@Column(name = "work_place")
	private String work_place;

	@Column(name = "image")
	private String image;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id",referencedColumnName = "id")
	private DoctorAddress address;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="sign_id",referencedColumnName = "id")
	private DoctorSign sign;

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
		
		tempAppointment.setDoctor(this);
	}
	public Doctor() {
		
	}
	
	public Doctor( String firstName, String midletName, String lastName, String sSID, String college,
			Date graduationYear, String specialization, String work_place ,int fees ,String image) {
		this.firstName = firstName;
		this.midletName = midletName;
		this.lastName = lastName;
		SSID = sSID;
		this.college = college;
		this.graduationYear = graduationYear;
		this.specialization = specialization;
		this.work_place = work_place;
		this.fees = fees;
		this.image=image;
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

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public Date getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(Date graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getWork_place() {
		return work_place;
	}

	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public DoctorAddress getAddress() {
		return address;
	}

	public void setAddress(DoctorAddress address) {
		this.address = address;
	}

	public DoctorSign getSign() {
		return sign;
	}

	public void setSign(DoctorSign sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", firstName=" + firstName + ", midletName=" + midletName + ", lastName=" + lastName
				+ ", SSID=" + SSID + ", college=" + college + ", graduationYear=" + graduationYear + ", specialization="
				+ specialization + ", work_place=" + work_place + ", address=" + address.toString() + ", sign=" + sign.toString() + "]";
	}

		
}
