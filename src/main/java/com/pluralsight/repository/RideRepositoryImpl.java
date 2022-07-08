package com.pluralsight.repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTermplate;
		
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTermplate.query("select * from ride", new RideRowMapper());
		
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
		// TODO Auto-generated method stub
		
		//jdbcTermplate.update("insert into ride (name,duration) values(?,?)",ride.getName(),ride.getDuration());
		
		/*
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTermplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = con.prepareStatement("insert into ride (name,duration) values(?,?)",
						new String[] {"id"});
				ps.setString(1, ride.getName());
				ps.setInt(2, ride.getDuration());
				
				return ps;
			}
		}, keyHolder);
		
		Number id = keyHolder.getKey();*/
		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTermplate);
		List<String> colums = new ArrayList<>();
		
		colums.add("name");
		colums.add("duration");
		
		insert.setTableName("ride");
		insert.setColumnNames(colums);
		
		Map<String,Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration", ride.getDuration());
		
		insert.setGeneratedKeyName("id");
		
		Number id = insert.executeAndReturnKey(data);
		
		return getRide(id.intValue());
	}
	
	@Override
	public Ride getRide(Integer id) {
		Ride ride = jdbcTermplate.queryForObject("select * from ride where id = ?", new RideRowMapper(),id);
		
		return ride;
		
	}

	@Override
	public Ride updateRide(Ride ride) {
		// TODO Auto-generated method stub
		jdbcTermplate.update("update ride set name = ?, duration = ? where id = ?",ride.getName(),ride.getDuration(),ride.getId());
		
		return ride;
	}

	@Override
	public void updateRides(List<Object[]> pairs) {
		// TODO Auto-generated method stub
		jdbcTermplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs);
		
	}
	
	
}
