package com.GP.testdemo.doctors.dao;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Patient;
import com.GP.testdemo.doctors.entity.PatientAddress;
import com.GP.testdemo.doctors.entity.PatientSign;



public interface PatientDAO {

	
	public List<Patient> findAllD();
	
	public Patient findDById(int theId);
	
	public void saveD(Patient thPatient);
	
	public void deleteDById(int theId);
	
	public PatientAddress findAbyId(int theId);
	
	public void saveA(PatientAddress thPatientAddress);
	
	public PatientSign findSbyId(int theId);
	
	public void saveS( PatientSign thePatientSign);

	void clear();

	public Patient PatientbyEmail(String email);
	
	public List<Appointment> findAllAppointments();
	
	public List<Appointment> findAppointmentByDoctortId(int theId);
	
	public List<Appointment> findAppointmentByDate(Date date);
	
	public Appointment addAppointment(Appointment theAppointment);
}


