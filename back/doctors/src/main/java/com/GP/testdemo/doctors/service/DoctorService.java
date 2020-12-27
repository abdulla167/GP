package com.GP.testdemo.doctors.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Doctor;
import com.GP.testdemo.doctors.entity.DoctorAddress;
import com.GP.testdemo.doctors.entity.DoctorSign;

public interface DoctorService {

	public List<Doctor> findAllD();
	
	public Doctor findDById(int theId);
	
	public void saveD(Doctor thDoctor);
	
	public void updateD(Doctor thDoctor);
	
	public void deleteDById(int theId);
	
	public DoctorAddress findAbyId(int theId);
	
	public void updateA(DoctorAddress thDoctorAddress);
	
	public void saveA(DoctorAddress thDoctorAddress);
	
	public DoctorSign findSbyId(int theId);
	
	public void saveS( DoctorSign theDoctorSign);
	
	public void updateS( DoctorSign theDoctorSign);

	void clear();
	
	public Doctor DoctorbyEmail(String email);
	public List<Appointment> findAllAppointments();
	
	public List<Appointment> findAppointmentByPatientId(int theId);
	
	public List<Appointment> findAppointmentByDate(Date date);
	
	public Appointment findAppointmentByDateTime(Timestamp dateTime);
}
