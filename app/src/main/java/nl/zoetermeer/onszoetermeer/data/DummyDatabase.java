package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import nl.zoetermeer.onszoetermeer.helpers.DateConverter;
import nl.zoetermeer.onszoetermeer.helpers.GenderConverter;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.User;

@Database(entities = {User.class, Challenge.class}, version = 4)
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
                            .fallbackToDestructiveMigration()
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

        private UserDAO userDao;
        private ChallengeDAO challengeDAO;

        PopulateDbAsync(DummyDatabase db) {
            userDao = db.userDAO();
            challengeDAO = db.challengeDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            userDao.deleteAll();

            User stefan = new User();
            stefan.setM_email("doodewaard@hotmail.com");
            stefan.setM_password("wachtwoord");
            stefan.setM_first_name("Stefan");
            stefan.setM_last_name("van Doodewaard");
            stefan.gender = User.Gender.Man;
            userDao.insert(stefan);

            User kenny = new User();
            kenny.setM_email("k.dillewaard@hotmail.com");
            kenny.setM_password("wachtwoord");
            kenny.setM_first_name("Kenny");
            kenny.setM_last_name("Dillewaard");
            kenny.gender = User.Gender.Man;
            userDao.insert(kenny);

            challengeDAO.deleteAll();

            List<Challenge> challenges = new ArrayList<Challenge>();

            challenges.add(new Challenge("Turbo lopen", "details", Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Kloppen", "details", Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Boksen", "details", Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Aanspannen & loslaten", "details", Challenge.VitalityType.Fysiek));
            challenges.add(new Challenge("Houthakken", "details", Challenge.VitalityType.Fysiek));

            challenges.add(new Challenge("Sudoku", "details", Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Kruiswoordpuzzel", "details", Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Zoek de verschillen", "details", Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Memocoly", "details", Challenge.VitalityType.Mentaal));
            challenges.add(new Challenge("Tik de juiste volgorde", "details", Challenge.VitalityType.Mentaal));

            challengeDAO.insertAll(challenges);




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("DATABASE:", "Test data (re)created.");
        }
    }

}
