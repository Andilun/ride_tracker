package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		
		Number id = keyHolder.getKey();
		
		return getRide(id.intValue());
	}
	
	public Ride getRide(Integer id) {
		Ride ride = jdbcTermplate.queryForObject("select * from ride where id = ?", new RideRowMapper(),id);
		
		return ride;
		
	}
	
	
}
