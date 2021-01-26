package com.GP.testdemo.doctors.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Inheritance(
		strategy = InheritanceType.JOINED
)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User implements Serializable{

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

	@Column(name="gender")
	private String gender;

	@Column(name="age")
	private int age;

	@Column(name="langtide")
	private double langtide;

	@Column(name="lantide")
	private double lantide;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id",referencedColumnName = "id")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="sign_id",referencedColumnName = "id")
	private SignIn sign;

	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "doctor",cascade = {CascadeType.DETACH,CascadeType.MERGE ,CascadeType.PERSIST,CascadeType.REFRESH})
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Appointment> doctorAppointments;

	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "patient",cascade = {CascadeType.DETACH,CascadeType.MERGE ,CascadeType.PERSIST,CascadeType.REFRESH})
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Appointment> patientAppointments;

	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "user",cascade = {CascadeType.DETACH,CascadeType.MERGE ,CascadeType.PERSIST,CascadeType.REFRESH})
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Contact> contacts;


	public User() {

	}


	public User(String firstName, String midletName, String lastName, String SSID, String image, String gender, int age, double langtide, double lantide) {
		this.firstName = firstName;
		this.midletName = midletName;
		this.lastName = lastName;
		this.SSID = SSID;
		this.image = image;
		this.gender = gender;
		this.age = age;
		this.langtide = langtide;
		this.lantide = lantide;
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

	public void setSSID(String SSID) {
		this.SSID = SSID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getLangtide() {
		return langtide;
	}

	public void setLangtide(double langtide) {
		this.langtide = langtide;
	}

	public double getLantide() {
		return lantide;
	}

	public void setLantide(double lantide) {
		this.lantide = lantide;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public SignIn getSign() {
		return sign;
	}

	public void setSign(SignIn sign) {
		this.sign = sign;
	}
	public List<Appointment> getDoctorAppointments() {
		return doctorAppointments;
	}

	public void setDoctorAppointments(List<Appointment> doctorAppointments) {
		this.doctorAppointments = doctorAppointments;
	}

	public List<Appointment> getPatientAppointments() {
		return patientAppointments;
	}

	public void setPatientAppointments(List<Appointment> patientAppointments) {
		this.patientAppointments = patientAppointments;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void addDoctorAppointment(Appointment tempAppointment) {

		if (doctorAppointments == null) {
			doctorAppointments = new ArrayList<Appointment>();
		}

		doctorAppointments.add(tempAppointment);

		tempAppointment.setDoctor(this);
	}

	public void addPatientAppointment(Appointment tempAppointment) {

		if (patientAppointments == null) {
			patientAppointments = new ArrayList<Appointment>();
		}

		patientAppointments.add(tempAppointment);

		tempAppointment.setPatient(this);
	}
	public void addContact(Contact tempContact) {

		if (contacts == null) {
			contacts = new ArrayList<Contact>();
		}

		contacts.add(tempContact);

		tempContact.setUser(this);
	}
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", midletName='" + midletName + '\'' +
				", lastName='" + lastName + '\'' +
				", SSID='" + SSID + '\'' +
				", gender='" + gender + '\'' +
				", age=" + age +
				'}';
	}
}
