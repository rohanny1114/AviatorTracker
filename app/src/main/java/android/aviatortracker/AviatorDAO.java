package android.aviatortracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface AviatorDAO {

    // DISPLAY all
    @Query("SELECT * FROM Aviator")
    List<Aviator> getAllFlights();

    // DELETE one
    @Delete
    int deleteFlight(Aviator a);

    // ADD one
    @Insert
    long insertFlight(Aviator a);
}
