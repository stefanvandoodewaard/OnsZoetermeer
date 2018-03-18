package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

            new PopulateDbAsync(INSTANCE).execute();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {

        INSTANCE = null;
        Log.i("DATABASE:", "Instance destroyed.");
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {

        private final UserDAO userDao;


        PopulateDbAsync(DummyDatabase db) {
            userDao = db.userDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            userDao.deleteAll();

            User user = new User();
            user.setM_email("test@test.nl");
            user.setM_password("wachtwoord");
            user.setM_first_name("Test1");
            user.setM_last_name("Test123");
            user.gender = User.Gender.Man;

            userDao.insert(user);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("DATABASE:", "Test data (re)created.");
        }
    }

}
