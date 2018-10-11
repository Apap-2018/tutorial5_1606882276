package com.apap.tutorial5.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.PilotDB;

@Service
@Transactional
public class PilotServiceImpl implements PilotService{
	@Autowired
	private PilotDB pilotDb;
	
	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		return pilotDb.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		pilotDb.save(pilot);
	}
	
	@Override
	public void deletePilot(PilotModel pilot) {
		pilotDb.delete(pilot);
	}
	
	//pilotv2 itu objek pilot dengan data baru, tapi gak dimasukin ke db
	@Override
	public void updatePilot(String licenseNumber, PilotModel pilotv2) {
		PilotModel pilotv1 = pilotDb.findByLicenseNumber(licenseNumber);
		pilotv1.setName(pilotv2.getName());
		pilotv1.setFlyHour(pilotv2.getFlyHour());
		pilotDb.save(pilotv1);
	}
}
