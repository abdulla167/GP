package com.GP.testdemo.doctors.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Doctor;
import com.GP.testdemo.doctors.entity.DoctorAddress;
import com.GP.testdemo.doctors.entity.DoctorSign;

@Repository
public class DoctorDaoImpl implements DoctorDAO{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Doctor> findAllD() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Doctor> theQuery = 
				currentSession.createQuery("from Doctor", Doctor.class);
		
		// execute the query
		List<Doctor> doctors = theQuery.getResultList();
		
		return doctors;
	}

	@Override
	public Doctor findDById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Doctor theDoctor = currentSession.get(Doctor.class, theId);
		
		return theDoctor;
	}

	@Override
	public void saveD(Doctor thDoctor) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(thDoctor);
	}

	@Override
	public void deleteDById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("delete from Doctor where id=:theId");
		theQuery.setParameter("theId", theId);
		
	}

	@Override
	public DoctorAddress findAbyId(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Doctor theDoctor = currentSession.get(Doctor.class, theId);
		
		return theDoctor.getAddress();
	}

	@Override
	public void saveA(DoctorAddress thDoctorAddress) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(thDoctorAddress);
		
	}

	@Override
	public DoctorSign findSbyId(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Doctor theDoctor = currentSession.get(Doctor.class, theId);
		
		return theDoctor.getSign();
	}

	@Override
	public void saveS(DoctorSign theDoctorSign) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(theDoctorSign);
		
	}
	
	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public Doctor DoctorbyEmail(String theEmail) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery(" from DoctorSign where email=:theEmail");
		List<DoctorSign> sign=query.setParameter("theEmail", theEmail).getResultList();
		 //List<DoctorSign>sign=query.getResultList();
		clear();
		query = currentSession.createQuery(" from Doctor where sign_id=:theId");
		List <Doctor>doctor =query.setParameter("theId", sign.get(0).getId()).getResultList();
		//List <Doctor>doctor =  query.getResultList(); 
		return doctor.get(0);
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
	public List<Appointment> findAppointmentByPatientId(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Appointment> theQuery = 
				currentSession.createQuery("from Appointment where patient_id:=theId");
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
	public Appointment findAppointmentByDateTime(Timestamp dateTime) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Appointment> theQuery = 
				currentSession.createQuery("from Appointment where date:=dateTime");
		Appointment appointment = theQuery.setParameter("dateTime", dateTime).getSingleResult();
		return appointment;
	}

}
