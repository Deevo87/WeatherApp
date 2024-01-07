package pl.edu.agh.to2.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.to2.example.database.Trip;
import pl.edu.agh.to2.example.database.TripService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(){
        return new ResponseEntity<List<Trip>>(tripService.getAllTrips(), HttpStatus.OK);
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip){

        return tripService.createTrip(trip.getStartLoc(), trip.getDestLoc(), trip.getDays());
    }


}
