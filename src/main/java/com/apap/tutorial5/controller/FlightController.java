package com.apap.tutorial5.controller;

import java.util.ArrayList;
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
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		ArrayList<FlightModel> fList = new ArrayList<FlightModel>();
		//FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		//flight.setPilot(pilot);
		fList.add(new FlightModel());
		System.out.println(pilot.getPilotFlight().size());
		pilot.setPilotFlight(fList);
		System.out.println(pilot.getPilotFlight().size());
		model.addAttribute("pilot", pilot);
		model.addAttribute("pageName", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params = {"save"}, method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute PilotModel pilot, Model model) {
		PilotModel addedPilot = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		System.out.println(addedPilot.getPilotFlight().size());
		System.out.println(pilot.getPilotFlight().size());
		for(FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(addedPilot);
			flightService.addFlight(flight);
		}
		model.addAttribute("pageName", "Add");
		return "add";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params = {"addRow"}, method = RequestMethod.POST)
	private String addRow(@ModelAttribute PilotModel pilot, Model model) {
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("pageName", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flightService.getFlightById(flight.getId()));
		}
		model.addAttribute("pageName", "Delete Flight");
//		flightService.deleteFlight(flightService.getFlightById(id));
		return "delete";
	}
	
	@RequestMapping(value = "/flight/view", method = RequestMethod.GET)
	public String view(@RequestParam("flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getFlightByFlightNumber(flightNumber);	
		model.addAttribute("flight", flight);
		model.addAttribute("pageName", "View Flight");
		return "view-flight";
	}

	@RequestMapping(value = "/flight/view/{id}", method = RequestMethod.GET)
	private String uviewFlightSubmit(@PathVariable(value = "id") long id, Model model) {
		FlightModel flight = flightService.getFlightById(id);
		model.addAttribute("flight", flight);
		model.addAttribute("pageName", "View FLight");
		return "view-flight";
	}


	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable(value = "id") long id, Model model) {
		FlightModel flight = flightService.getFlightById(id);
		model.addAttribute("flight", flight);
		model.addAttribute("pageName", "Update Flight");
		return "updateFlight";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
	private String updateFlightSubmit(@PathVariable(value = "id") long id, Model model,  @ModelAttribute FlightModel flightU) {
		flightService.updateFlight(id, flightU);
		model.addAttribute("pageName", "Update");
		return "update";
	}
}
