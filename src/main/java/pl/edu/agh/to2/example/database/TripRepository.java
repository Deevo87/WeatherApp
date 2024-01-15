package pl.edu.agh.to2.example.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TripRepository extends MongoRepository<Trip, String> {
    Trip findByLocationsAndDays(List<String> locations, int days);
}
