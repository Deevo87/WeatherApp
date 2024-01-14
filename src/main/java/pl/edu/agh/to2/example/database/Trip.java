package pl.edu.agh.to2.example.database;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="trips")
@Data
public class Trip {
    @Id
    private ObjectId id;

    private List<String> locations;
    private int days;

    public Trip(List<String> locations, int days){
        this.locations = locations;
        this.days = days;
    }

}

