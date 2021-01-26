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
public class Doctor extends User implements Serializable{

	@Column(name="fees")
	private int fees;

	@Column(name="college")
	private String college;

	@Column(name="graduation_year")
	private Date graduationYear;

	@Column(name="specialization")
	private String specialization;

	@Column(name = "work_place")
	private String work_place;

	public Doctor() {

	}

	public Doctor(String firstName, String midletName, String lastName, String SSID, String image, String gender, int age, double langtide, double lantide, int fees, String college, Date graduationYear, String specialization, String work_place) {
		super(firstName, midletName, lastName, SSID, image, gender, age, langtide, lantide);
		this.fees = fees;
		this.college = college;
		this.graduationYear = graduationYear;
		this.specialization = specialization;
		this.work_place = work_place;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
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


	@Override
	public String toString() {
		return "Doctor{" +
				"fees=" + fees +
				", college='" + college + '\'' +
				", graduationYear=" + graduationYear +
				", specialization='" + specialization + '\'' +
				", work_place='" + work_place + '\'' +
				'}' + super.toString();
	}
}
