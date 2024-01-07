package pl.edu.agh.to2.example.database;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    public List<Trip> getAllTrips(){
        return tripRepository.findAll();
    }

    public Trip createTrip(String startLoc, String destLoc, int days){
        Trip trip = new Trip(startLoc, destLoc, days);
        tripRepository.insert(trip);

        return trip;
    }
}
