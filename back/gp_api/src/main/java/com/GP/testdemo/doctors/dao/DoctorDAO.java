package com.GP.testdemo.doctors.dao;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.GP.testdemo.doctors.entity.Address;
import com.GP.testdemo.doctors.entity.SignIn;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Doctor;



public interface DoctorDAO {

	
	public List<Doctor> findAllD();
	
	public Doctor findDById(int theId);
	
	public void saveD(Doctor thDoctor);
	
	public void deleteDById(int theId);
	
	public Address findAbyId(int theId);
	
	public void saveA(Address thDoctorAddress);
	
	public SignIn findSbyId(int theId);
	
	public void saveS( SignIn theDoctorSign);

	void clear();

	public Doctor DoctorbyEmail(String email);
	
	public List<Appointment> findAllAppointments();
	
	public List<Appointment> findAppointmentByPatientId(int theId);
	
	public List<Appointment> findAppointmentByDate(Date date);
	
	public Appointment findAppointmentByDateTime(Timestamp dateTime);
}


