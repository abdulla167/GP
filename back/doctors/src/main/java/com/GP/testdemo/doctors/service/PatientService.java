package com.GP.testdemo.doctors.service;

import java.sql.Date;
import java.util.List;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Patient;
import com.GP.testdemo.doctors.entity.PatientAddress;
import com.GP.testdemo.doctors.entity.PatientSign;

public interface PatientService {

	public List<Patient> findAllD();
	
	public Patient findDById(int theId);
	
	public void saveD(Patient thPatient);
	
	public void updateD(Patient thPatient);
	
	public void deleteDById(int theId);
	
	public PatientAddress findAbyId(int theId);
	
	public void updateA(PatientAddress thPatientAddress);
	
	public void saveA(PatientAddress thPatientAddress);
	
	public PatientSign findSbyId(int theId);
	
	public void saveS( PatientSign thePatientSign);
	
	public void updateS( PatientSign thePatientSign);

	void clear();
	
	public Patient PatientbyEmail(String email);
	
	public List<Appointment> findAllAppointments();
	
	public List<Appointment> findAppointmentByDoctortId(int theId);
	
	public List<Appointment> findAppointmentByDate(Date date);
	
	public Appointment addAppointment(Appointment theAppointment);
}
