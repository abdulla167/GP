package com.GP.testdemo.doctors.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.GP.testdemo.doctors.entity.Address;
import com.GP.testdemo.doctors.entity.SignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GP.testdemo.doctors.dao.DoctorDAO;
import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Doctor;


@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorDAO theDoctorDAO;
	
	@Override
	@Transactional 
	public List<Doctor> findAllD() {

		return theDoctorDAO.findAllD();
	}

	@Override
	@Transactional 
	public Doctor findDById(int theId) {
		
		return theDoctorDAO.findDById(theId);
	}

	@Override
	@Transactional 
	public void saveD(Doctor thDoctor) {
		
		thDoctor.setId(0);
		theDoctorDAO.saveD(thDoctor);
	}
	
	@Override
	@Transactional 
	public void updateD(Doctor thDoctor) {
		
		theDoctorDAO.saveD(thDoctor);
	}

	@Override
	@Transactional 
	public void deleteDById(int theId) {
		
		theDoctorDAO.deleteDById(theId);
	}

	@Override
	@Transactional 
	public Address findAbyId(int theId) {
		
		return theDoctorDAO.findAbyId(theId);
	}

	@Override
	@Transactional 
	public void saveA(Address thDoctorAddress) {
		
		thDoctorAddress.setId(0);
		theDoctorDAO.saveA(thDoctorAddress);
		
	}
	
	@Override
	@Transactional 
	public void updateA(Address thDoctorAddress) {
		
		theDoctorDAO.saveA(thDoctorAddress);
	}

	@Override
	@Transactional 
	public SignIn findSbyId(int theId) {
		
		return theDoctorDAO.findSbyId(theId);
	}

	@Override
	@Transactional 
	public void updateS(SignIn theDoctorSign) {

		theDoctorDAO.saveS(theDoctorSign);
	}


	@Override
	@Transactional 
	public void saveS(SignIn theDoctorSign) {
		
		theDoctorSign.setId(0);
		theDoctorDAO.saveS(theDoctorSign);
	}
	@Override
	@Transactional 
	public void clear() {
		
		theDoctorDAO.clear();
	}

	@Override
	@Transactional
	public Doctor DoctorbyEmail(String email) {
		
		return theDoctorDAO.DoctorbyEmail(email);
	}

	@Override
	@Transactional
	public List<Appointment> findAllAppointments() {
		// TODO Auto-generated method stub
		return theDoctorDAO.findAllAppointments();
	}

	@Override
	@Transactional
	public List<Appointment> findAppointmentByPatientId(int theId) {
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
	public Appointment findAppointmentByDateTime(Timestamp dateTime) {
		// TODO Auto-generated method stub
		return null;
	}
}
