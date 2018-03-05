package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.Models.UserDAO;

@Database(entities = {User.class}, version = 2)
@TypeConverters({Converters.class})
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
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void createData(Context context) {
        User user1 = new User();

        user1.setM_email("tante_jannie@casema.nl");
        user1.setM_password("password");
        user1.gender = User.Gender.Vrouw;
        user1.setM_first_name("Jannie");
        user1.setM_last_name("Jansen");


        INSTANCE.userDAO().insert(user1);


    }
}
