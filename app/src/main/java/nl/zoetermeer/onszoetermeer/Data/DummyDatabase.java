package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;
import java.util.Date;

import nl.zoetermeer.onszoetermeer.Helpers.DateConverter;
import nl.zoetermeer.onszoetermeer.Helpers.GenderConverter;
import nl.zoetermeer.onszoetermeer.Models.User;

@Database(entities = {User.class}, version = 2)
@TypeConverters({DateConverter.class, GenderConverter.class})
public abstract class DummyDatabase extends RoomDatabase
{
    public abstract UserDAO userDAO();

    private static DummyDatabase INSTANCE;

    public static DummyDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DummyDatabase.class, "DUMMY_DATABASE")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
            Log.i("DATABASE:", "New instance created.");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {

        INSTANCE = null;
        Log.i("DATABASE:", "Instance destroyed.");
    }

    public void createData(Context context) {
        User user1 = new User();

        user1.setM_email("tante_jannie@casema.nl");
        user1.setM_password("password");
        user1.gender = User.Gender.Vrouw;
        user1.setM_first_name("Jannie");
        user1.setM_last_name("Jansen");
        Date date = new Date();
        user1.setM_last_active(date);

        INSTANCE.userDAO().insert(user1);

        Log.i("DATABASE:", "Test Data created");

    }
}
