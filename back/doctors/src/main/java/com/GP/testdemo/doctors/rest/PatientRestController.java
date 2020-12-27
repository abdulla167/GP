package com.GP.testdemo.doctors.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.GP.testdemo.doctors.entity.*;
import com.GP.testdemo.doctors.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.GP.testdemo.doctors.service.PatientService;

@RestController
//@SessionAttributes(("user"))
@RequestMapping("/patient")
public class PatientRestController {
	
	@Autowired
	PatientService thePatientService;
	@Autowired
	DoctorService theDoctorService;

	
	@ModelAttribute("user")
	public Patient doctor() {
		return new Patient();
	}
	
	@GetMapping("/appointments")
	public List<Appointment> findAppointments(){
		return thePatientService.findAllAppointments();
	}
	
	@PostMapping("/appointments")
	public Appointment addAppointment(@RequestBody Appointment theAppointment,HttpSession session) {
		Patient patient =(Patient) session.getAttribute("user");
		Doctor doctor = theDoctorService.findDById(theAppointment.getDoctor().getId());
		doctor.add(theAppointment);
		theDoctorService.clear();
		//System.out.println(patient.toString());
		patient.add(theAppointment);
		return thePatientService.addAppointment(theAppointment);

	}
	
	@GetMapping("/patients")
	public List<Patient> getPatients(Model theModel,HttpSession session){
		try {
			
			//Patient doctor =(Patient) session.getAttribute("User");
			Patient patient =(Patient) session.getAttribute("user");
			System.out.println(patient.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage() );
		}
		return thePatientService.findAllD();
	}
	
	@GetMapping("/patients/{theId}")
	public Patient findPatient(@PathVariable int theId) {
		return thePatientService.findDById(theId);
	}
	
	@PostMapping("/patients")
	public Patient addPatient(@RequestBody Patient thePatient) {
//		PatientAddress address = thePatient.getAddress();
//		PatientSign sign = thePatient.getSign();
		
//		thePatientService.saveA(thePatient.getAddress());
//		thePatientService.saveS(thePatient.getSign());
		
		thePatientService.saveD(thePatient);
		return thePatient;
	}
	
	@PutMapping("/patients")
	public Patient updatePatient(@RequestBody Patient thePatient) {
		
//		PatientAddress address = thePatient.getAddress();
//		PatientSign sign = thePatient.getSign(); 
		Patient dbPatient = 	thePatientService.findDById(thePatient.getId());
		
		thePatient.getAddress().setId(dbPatient.getAddress().getId());
		thePatient.getSign().setId(dbPatient.getSign().getId());
		thePatientService.clear();
//		System.out.println(thePatient.getAddress());
//		thePatientService.updateA(thePatient.getAddress());
//		thePatientService.updateS(thePatient.getSign());
		thePatientService.updateD(thePatient);
		
		return thePatient;
	}
	@DeleteMapping("/patients/{theId}")
	public String deletePatient(@PathVariable int theId) {
		thePatientService.deleteDById(theId);
		
		return "Patient deleted id-"+theId;
	}
	
	@PostMapping("/patients/SignIn")
	public String signPatient(@RequestBody  PatientSign patientSign, HttpServletRequest session) {
		
		Patient dbPatient = thePatientService.PatientbyEmail(patientSign.getEmail());
		
		if(dbPatient.getSign().getPassword().equals( patientSign.getPassword() )) {
			//session.getSession().invalidate();
			session.getSession().setAttribute("user", dbPatient);
//			Patient doctor =(Patient) theModel.getAttribute("User");
//			System.out.println(doctor.toString());
			return " Succesful Sign in";
		}else {
			return "Failure";
		}
	}
}
