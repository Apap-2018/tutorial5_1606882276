package com.apap.tutorial5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/")
	private String home(Model model) {
		model.addAttribute("pageName", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add (Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("pageName", "Add Pilot");
		return "addPilot";
	}
	
	//pilot itu dibikin pas disubmit di addPilot.html trus disimpen di db lewat yang ini
	//@modelattribute buat ngambil data yang dibikin di addPilot
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("pageName", "Add");
		return "add";
	}
	
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	public String viewPilot(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);	
//		List<FlightModel> listOfFlights = pilot.getPilotFlight();
		model.addAttribute("pilot", pilot);
//		model.addAttribute("listOfFlights", listOfFlights);
		model.addAttribute("pageName", "View Pilot");
		return "view-pilot";
	}
	
	@RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
	private String deleteFlightSubmit(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		pilotService.deletePilot(pilotService.getPilotDetailByLicenseNumber(licenseNumber));
		model.addAttribute("pageName", "Delete Pilot");
		return "delete";
	}
	
	//
	//
	//
	
	//buat naro data pilot yang udah ada ke htmlnya
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		model.addAttribute("pageName", "Update Pilot");
		return "updatePilot";
	}
	
	//ini buat ngirim ke servicenya, pilotU itu objek sementara yang gak dimasukin ke db, abis submit ini dijalanin
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.POST)
	private String updatePilotSubmit(@PathVariable(value = "licenseNumber") String licenseNumber, Model model,  @ModelAttribute PilotModel pilotU) {
		pilotService.updatePilot(licenseNumber, pilotU);
		model.addAttribute("pageName", "Update Flight");
		return "update";
	}
}
