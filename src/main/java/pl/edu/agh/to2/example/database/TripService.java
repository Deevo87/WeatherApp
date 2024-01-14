package pl.edu.agh.to2.example.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips(){
        return tripRepository.findAll();
    }

    public Trip createTrip(List<String> locations, int days){
        Trip trip = new Trip(locations, days);
        tripRepository.insert(trip);

        return trip;
    }
}
