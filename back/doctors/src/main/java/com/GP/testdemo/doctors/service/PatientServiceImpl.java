package com.GP.testdemo.doctors.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GP.testdemo.doctors.dao.PatientDAO;
import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Patient;
import com.GP.testdemo.doctors.entity.PatientAddress;
import com.GP.testdemo.doctors.entity.PatientSign;

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
	public PatientAddress findAbyId(int theId) {
		
		return thePatientDAO.findAbyId(theId);
	}

	@Override
	@Transactional 
	public void saveA(PatientAddress thPatientAddress) {
		
		thPatientAddress.setId(0);
		thePatientDAO.saveA(thPatientAddress);
		
	}
	
	@Override
	@Transactional 
	public void updateA(PatientAddress thPatientAddress) {
		
		thePatientDAO.saveA(thPatientAddress);
	}

	@Override
	@Transactional 
	public PatientSign findSbyId(int theId) {
		
		return thePatientDAO.findSbyId(theId);
	}

	@Override
	@Transactional 
	public void updateS(PatientSign thePatientSign) {

		thePatientDAO.saveS(thePatientSign);
	}


	@Override
	@Transactional 
	public void saveS(PatientSign thePatientSign) {
		
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
