package com.GP.testdemo.doctors.service;

import java.sql.Date;
import java.util.List;

import com.GP.testdemo.doctors.entity.Address;
import com.GP.testdemo.doctors.entity.SignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GP.testdemo.doctors.dao.PatientDAO;
import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Patient;


@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientDAO thePatientDAO;
	
	@Override
	@Transactional 
	public List<Patient> findAllD() {

		return thePatientDAO.findAllD();
	}

	@Override
	@Transactional 
	public Patient findDById(int theId) {
		
		return thePatientDAO.findDById(theId);
	}

	@Override
	@Transactional 
	public void saveD(Patient thPatient) {
		
		thPatient.setId(0);
		thePatientDAO.saveD(thPatient);
	}
	
	@Override
	@Transactional 
	public void updateD(Patient thPatient) {
		
		thePatientDAO.saveD(thPatient);
	}

	@Override
	@Transactional 
	public void deleteDById(int theId) {
		
		thePatientDAO.deleteDById(theId);
	}

	@Override
	@Transactional 
	public Address findAbyId(int theId) {
		
		return thePatientDAO.findAbyId(theId);
	}

	@Override
	@Transactional 
	public void saveA(Address thPatientAddress) {
		
		thPatientAddress.setId(0);
		thePatientDAO.saveA(thPatientAddress);
		
	}
	
	@Override
	@Transactional 
	public void updateA(Address thPatientAddress) {
		
		thePatientDAO.saveA(thPatientAddress);
	}

	@Override
	@Transactional 
	public SignIn findSbyId(int theId) {
		
		return thePatientDAO.findSbyId(theId);
	}

	@Override
	@Transactional 
	public void updateS(SignIn thePatientSign) {

		thePatientDAO.saveS(thePatientSign);
	}


	@Override
	@Transactional 
	public void saveS(SignIn thePatientSign) {
		
		thePatientSign.setId(0);
		thePatientDAO.saveS(thePatientSign);
	}
	@Override
	@Transactional 
	public void clear() {
		
		thePatientDAO.clear();
	}

	@Override
	@Transactional
	public Patient PatientbyEmail(String email) {
		
		return thePatientDAO.PatientbyEmail(email);
	}

	@Override
	@Transactional
	public List<Appointment> findAllAppointments() {
		// TODO Auto-generated method stub
		return thePatientDAO.findAllAppointments();
	}

	@Override
	@Transactional
	public List<Appointment> findAppointmentByDoctortId(int theId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Appointment> findAppointmentByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Appointment addAppointment(Appointment theAppointment) {

		return thePatientDAO.addAppointment(theAppointment);
	}
}
