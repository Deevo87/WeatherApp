package pl.edu.agh.to2.example.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.to2.example.database.Trip;
import pl.edu.agh.to2.example.database.TripService;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;
    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(){
        return new ResponseEntity<List<Trip>>(tripService.getAllTrips(), HttpStatus.OK);
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip){
        return tripService.createTrip(trip.getLocations(), trip.getDays());
    }

    @DeleteMapping
    public void deleteTrip(@RequestBody Trip trip){
        tripService.deleteTrip(trip.getId());
    }


}
