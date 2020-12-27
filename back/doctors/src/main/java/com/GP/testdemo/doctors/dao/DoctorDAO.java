package com.GP.testdemo.doctors.dao;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Doctor;
import com.GP.testdemo.doctors.entity.DoctorAddress;
import com.GP.testdemo.doctors.entity.DoctorSign;



public interface DoctorDAO {

	
	public List<Doctor> findAllD();
	
	public Doctor findDById(int theId);
	
	public void saveD(Doctor thDoctor);
	
	public void deleteDById(int theId);
	
	public DoctorAddress findAbyId(int theId);
	
	public void saveA(DoctorAddress thDoctorAddress);
	
	public DoctorSign findSbyId(int theId);
	
	public void saveS( DoctorSign theDoctorSign);

	void clear();

	public Doctor DoctorbyEmail(String email);
	
	public List<Appointment> findAllAppointments();
	
	public List<Appointment> findAppointmentByPatientId(int theId);
	
	public List<Appointment> findAppointmentByDate(Date date);
	
	public Appointment findAppointmentByDateTime(Timestamp dateTime);
}


