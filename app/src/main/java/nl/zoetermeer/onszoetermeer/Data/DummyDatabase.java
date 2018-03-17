package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import nl.zoetermeer.onszoetermeer.Helpers.DateConverter;
import nl.zoetermeer.onszoetermeer.Helpers.GenderConverter;
import nl.zoetermeer.onszoetermeer.Models.Challenge;
import nl.zoetermeer.onszoetermeer.Models.User;

@Database(entities = {User.class, Challenge.class}, version = 3)
@TypeConverters({DateConverter.class, GenderConverter.class, Challenge.VitalityType.class})
public abstract class DummyDatabase extends RoomDatabase
{
    public abstract UserDAO userDAO();
    public abstract ChallengeDAO challengeDAO();

    private static DummyDatabase INSTANCE;

    public static DummyDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DummyDatabase.class, "DUMMY_DATABASE")
//                            .allowMainThreadQueries()
//                            .fallbackToDestructiveMigration()
                            .build();
            Log.i("DATABASE:", "New instance created.");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {

        INSTANCE = null;
        Log.i("DATABASE:", "Instance destroyed.");
    }

}
