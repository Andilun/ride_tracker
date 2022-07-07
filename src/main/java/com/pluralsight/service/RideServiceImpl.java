package com.pluralsight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

	@Override
	public Ride createRide(Ride ride) {
		// TODO Auto-generated method stub
		return rideRepository.createRide(ride);
	}

	@Override
	public Ride getRide(Integer id) {
		// TODO Auto-generated method stub
		return rideRepository.getRide(id);
	}

	@Override
	public Ride updateRide(Ride ride) {
		// TODO Auto-generated method stub
		return rideRepository.updateRide(ride);
	}
	

}
