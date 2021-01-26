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
public class Patient  extends User implements Serializable {


	@Column(name = "history")
	private String history;

	public Patient(){

	}

	public Patient(String firstName, String midletName, String lastName, String SSID, String image, String gender, int age, double langtide, double lantide, String history) {
		super(firstName, midletName, lastName, SSID, image, gender, age, langtide, lantide);
		this.history = history;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public String toString() {

		return "Patient{" +
				"history='" + history + '\'' +
				'}' + super.toString();
	}
}
