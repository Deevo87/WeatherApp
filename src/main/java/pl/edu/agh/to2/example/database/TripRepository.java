package pl.edu.agh.to2.example.database;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TripRepository extends MongoRepository<Trip, ObjectId> {
    Trip findByLocationsAndDays(List<String> locations, int days);
}
