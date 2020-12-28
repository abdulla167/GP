package com.GP.testdemo.doctors.service;

import java.sql.Date;
import java.util.List;

import com.GP.testdemo.doctors.entity.Address;
import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Patient;
import com.GP.testdemo.doctors.entity.SignIn;

public interface PatientService {

	public List<Patient> findAllD();
	
	public Patient findDById(int theId);
	
	public void saveD(Patient thPatient);
	
	public void updateD(Patient thPatient);
	
	public void deleteDById(int theId);
	
	public Address findAbyId(int theId);
	
	public void updateA(Address thPatientAddress);
	
	public void saveA(Address thPatientAddress);
	
	public SignIn findSbyId(int theId);
	
	public void saveS( SignIn thePatientSign);
	
	public void updateS( SignIn thePatientSign);

	void clear();
	
	public Patient PatientbyEmail(String email);
	
	public List<Appointment> findAllAppointments();
	
	public List<Appointment> findAppointmentByDoctortId(int theId);
	
	public List<Appointment> findAppointmentByDate(Date date);
	
	public Appointment addAppointment(Appointment theAppointment);
}
