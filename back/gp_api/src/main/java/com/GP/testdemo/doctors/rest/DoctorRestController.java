package com.GP.testdemo.doctors.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.GP.testdemo.doctors.entity.SignIn;
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

import com.GP.testdemo.doctors.entity.Appointment;
import com.GP.testdemo.doctors.entity.Doctor;
import com.GP.testdemo.doctors.service.DoctorService;

@RestController
//@SessionAttributes(("user"))
@RequestMapping("/doctor")
public class DoctorRestController {
	
	@Autowired
	DoctorService theDoctorService;
	
	
	
	@ModelAttribute("user")
	public Doctor doctor() {
		return new Doctor();
	}
	
	@GetMapping("/appointments")
	public List<Appointment> findAppointments(){
		return theDoctorService.findAllAppointments();
	}
	
	@GetMapping("/doctors")
	public List<Doctor> getDoctors(Model theModel,HttpSession session){
		try {
			
			//Doctor doctor =(Doctor) session.getAttribute("User");
			Doctor doctor =(Doctor) session.getAttribute("user");
			System.out.println(doctor.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage() );
		}
		return theDoctorService.findAllD();
	}
	
	@GetMapping("/doctors/{theId}")
	public Doctor findDoctor(@PathVariable int theId) {
		return theDoctorService.findDById(theId);
	}
	
	@PostMapping("/doctors")
	public Doctor addDoctor(@RequestBody Doctor theDoctor) {
//		DoctorAddress address = theDoctor.getAddress();
//		DoctorSign sign = theDoctor.getSign();
		
//		theDoctorService.saveA(theDoctor.getAddress());
//		theDoctorService.saveS(theDoctor.getSign());
		theDoctor.getSign().setEnabled(0);
		theDoctorService.saveD(theDoctor);
		return theDoctor;
	}
	
	@PutMapping("/doctors")
	public Doctor updateDoctor(@RequestBody Doctor theDoctor) {
		
//		DoctorAddress address = theDoctor.getAddress();
//		DoctorSign sign = theDoctor.getSign(); 
		Doctor dbDoctor = 	theDoctorService.findDById(theDoctor.getId());
		
		theDoctor.getAddress().setId(dbDoctor.getAddress().getId());
		theDoctor.getSign().setId(dbDoctor.getSign().getId());
		theDoctorService.clear();
//		System.out.println(theDoctor.getAddress());
//		theDoctorService.updateA(theDoctor.getAddress());
//		theDoctorService.updateS(theDoctor.getSign());
		theDoctorService.updateD(theDoctor);
		
		return theDoctor;
	}
	@DeleteMapping("/doctors/{theId}")
	public String deleteDoctor(@PathVariable int theId) {
		theDoctorService.deleteDById(theId);
		
		return "Doctor deleted id-"+theId;
	}
	
	@PostMapping("/doctors/SignIn")
	public String signDoctor(@RequestBody SignIn doctorSign, Model theModel, HttpServletRequest session) {
		
		Doctor dbDoctor = theDoctorService.DoctorbyEmail(doctorSign.getEmail());
		
		if(dbDoctor.getSign().getPassword().equals( doctorSign.getPassword() )) {
			try {
				session.getSession().removeAttribute("user");
			}catch (Exception E){
				System.out.println("kkk \n lkk \n lll\n mmmm");
			}
			session.getSession().setAttribute("user", dbDoctor);
//			Doctor doctor =(Doctor) theModel.getAttribute("User");
//			System.out.println(doctor.toString());
			dbDoctor.getSign().setEnabled(1);
			theDoctorService.updateD(dbDoctor);
			return " Succesful Sign in";
		}else {
			dbDoctor.getSign().setEnabled(0);
			theDoctorService.updateD(dbDoctor);
			return "Failure";
		}
	}
}
