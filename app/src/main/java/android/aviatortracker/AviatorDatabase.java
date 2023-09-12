package android.aviatortracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Aviator.class}, version = 1)
public abstract class AviatorDatabase extends RoomDatabase {
    public abstract AviatorDAO aDAO();
}
