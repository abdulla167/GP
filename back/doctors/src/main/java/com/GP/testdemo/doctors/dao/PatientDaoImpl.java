package com.GP.testdemo.doctors.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Patient;
import com.GP.testdemo.doctors.entity.PatientAddress;
import com.GP.testdemo.doctors.entity.PatientSign;

@Repository
public class PatientDaoImpl implements PatientDAO{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Patient> findAllD() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Patient> theQuery = 
				currentSession.createQuery("from Patient", Patient.class);
		
		// execute the query
		List<Patient> patients = theQuery.getResultList();
		
		return patients;
	}

	@Override
	public Patient findDById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Patient thePatient = currentSession.get(Patient.class, theId);
		
		return thePatient;
	}

	@Override
	public void saveD(Patient thPatient) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(thPatient);
	}

	@Override
	public void deleteDById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("delete from Patient where id=:theId");
		theQuery.setParameter("theId", theId);
		
	}

	@Override
	public PatientAddress findAbyId(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Patient thePatient = currentSession.get(Patient.class, theId);
		
		return thePatient.getAddress();
	}

	@Override
	public void saveA(PatientAddress thPatientAddress) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(thPatientAddress);
		
	}

	@Override
	public PatientSign findSbyId(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Patient thePatient = currentSession.get(Patient.class, theId);
		
		return thePatient.getSign();
	}

	@Override
	public void saveS(PatientSign thePatientSign) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(thePatientSign);
		
	}
	
	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public Patient PatientbyEmail(String theEmail) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery(" from PatientSign where email=:theEmail");
		List<PatientSign> sign=query.setParameter("theEmail", theEmail).getResultList();
		 //List<PatientSign>sign=query.getResultList();
		clear();
		query = currentSession.createQuery(" from Patient where sign_id=:theId");
		List <Patient>patient =query.setParameter("theId", sign.get(0).getId()).getResultList();
		//List <Patient>doctor =  query.getResultList(); 
		return patient.get(0);
	}

	@Override
	public List<Appointment> findAllAppointments() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Appointment> theQuery = 
				currentSession.createQuery("from Appointment", Appointment.class);
		
		// execute the query
		List<Appointment> appointments = theQuery.getResultList();
		
		return appointments;
	}

	@Override
	public List<Appointment> findAppointmentByDoctortId(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Appointment> theQuery = 
				currentSession.createQuery("from Appointment where doctor_id:=theId");
		List<Appointment> appointments = theQuery.setParameter("theId", theId).getResultList();
		
		return appointments;
	}

	@Override
	public List<Appointment> findAppointmentByDate(Date theDate) {

//		Session currentSession = entityManager.unwrap(Session.class);
//		Query<Appointment> theQuery = 
//				currentSession.createQuery("from Appointment where date:=theDate");
//		List<Appointment> appointments = theQuery.setParameter("theDate", theDate).getResultList();
//		return appointments;
		return null;
	}

	@Override
	public Appointment addAppointment(Appointment theAppointment) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(theAppointment);
		return theAppointment;
	}

}
