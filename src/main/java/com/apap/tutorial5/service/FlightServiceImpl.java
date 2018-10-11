package com.apap.tutorial5.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.FlightDB;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightDB flightDb;
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDb.save(flight);
	}
	
	@Override
	public void deleteFlight(FlightModel flight) {
		flightDb.delete(flight);
	}
	
	@Override
	public FlightModel getFlightById(long Id) {
		return flightDb.findById(Id);
	}
	
	@Override
	public FlightModel getFlightByFlightNumber(String flightNumber) {
		return flightDb.findByFlightNumber(flightNumber);
	}
	
	//
	//
	//
	
	//dapet flightv2 dari submitan yang di addflight.html melalui controller
	@Override
	public void updateFlight(long id, FlightModel flightv2) {
		FlightModel flightv1 = flightDb.findById(id);
		flightv1.setDestination(flightv2.getDestination());
		flightv1.setFlightNumber(flightv2.getFlightNumber());
		flightv1.setOrigin(flightv2.getOrigin());
		flightv1.setTime(flightv2.getTime());
		flightDb.save(flightv1);
	}
}

