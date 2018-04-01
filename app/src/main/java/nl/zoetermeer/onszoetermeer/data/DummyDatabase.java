package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.zoetermeer.onszoetermeer.helpers.BadgeTypeConverter;
import nl.zoetermeer.onszoetermeer.helpers.DateConverter;
import nl.zoetermeer.onszoetermeer.helpers.GenderTypeConverter;
import nl.zoetermeer.onszoetermeer.helpers.VitalityTypeConverter;
import nl.zoetermeer.onszoetermeer.models.Achievement;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.models.UserAchievements;

@Database(entities = {User.class, Challenge.class, Achievement.class, UserAchievements.class}, version = 2)
@TypeConverters({DateConverter.class, GenderTypeConverter.class, VitalityTypeConverter.class, BadgeTypeConverter.class})
public abstract class DummyDatabase extends RoomDatabase
{
    public abstract UserDAO userDAO();
    public abstract ChallengeDAO challengeDAO();
    public abstract AchievementDAO achievementDAO();
    public abstract UserAchievementsDAO userAchievementsDAO();

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

        private UserDAO userDAO;
        private ChallengeDAO challengeDAO;
        private AchievementDAO achievementDAO;
        private UserAchievementsDAO userAchievementsDAO;

        PopulateDbAsync(DummyDatabase db) {
            userDAO = db.userDAO();
            challengeDAO = db.challengeDAO();
            achievementDAO = db.achievementDAO();
            userAchievementsDAO = db.userAchievementsDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            userDAO.deleteAll();
            User stefan = new User();
            stefan.setM_email("doodewaard@hotmail.com");
            stefan.setM_password("wachtwoord");
            stefan.setM_first_name("Stefan");
            stefan.setM_last_name("van Doodewaard");
            stefan.gender = User.Gender.Man;
            userDAO.insert(stefan);
            User kenny = new User();
            kenny.setM_email("k.dillewaard@hotmail.com");
            kenny.setM_password("wachtwoord");
            kenny.setM_first_name("Kenny");
            kenny.setM_last_name("Dillewaard");
            kenny.gender = User.Gender.Man;
            userDAO.insert(kenny);

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

            User testKenny = userDAO.getByEmail("k.dillewaard@hotmail.com");
            User testStefan = userDAO.getByEmail("doodewaard@hotmail.com");
            int testIdKenny = testKenny.getId();
            int testIdStefan = testStefan.getId();

            achievementDAO.deleteAll();
            List<Achievement> achievements = new ArrayList<Achievement>();
            List<UserAchievements> userAchievements = new ArrayList<UserAchievements>();

            achievements.add(new Achievement("Test #1", Achievement.BadgeType.Goud));
            achievements.add(new Achievement("Test #2", Achievement.BadgeType.Zilver));
            achievements.add(new Achievement("Test #3", Achievement.BadgeType.Brons));
            achievements.add(new Achievement("Test #4", Achievement.BadgeType.Goud));
            achievements.add(new Achievement("Test #5", Achievement.BadgeType.Zilver));
            achievements.add(new Achievement("Test #6", Achievement.BadgeType.Brons));
            achievementDAO.insertAll(achievements);

            achievements = achievementDAO.getAllAchievements();


            for(int i = 0; i < 3; i++) {
                int achievementId = achievements.get(i).getID();
                userAchievements.add(new UserAchievements(testIdKenny, achievementId, new Date()));
                userAchievements.add(new UserAchievements(testIdStefan, achievementId, new Date()));
            }
            for(int i = 3; i < 6; i++) {
                int achievementId = achievements.get(i).getID();
                userAchievements.add(new UserAchievements(testIdKenny, achievementId, null));
                userAchievements.add(new UserAchievements(testIdStefan, achievementId, null));
            }
            userAchievementsDAO.insertAll(userAchievements);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("DATABASE:", "Test data (re)created.");
        }
    }

}
