package pl.edu.agh.to2.example.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="trips")
@Data
public class Trip {
    @Id
    private ObjectId id;

    private String startLoc;
    private String destLoc;
    private int days;

    public Trip(String startLoc, String destLoc, int days){
        this.startLoc = startLoc;
        this.destLoc = destLoc;
        this.days = days;
    }

}

