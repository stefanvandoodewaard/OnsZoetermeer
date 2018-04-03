package nl.zoetermeer.onszoetermeer.helpers;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import nl.zoetermeer.onszoetermeer.data.AchievementDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserAchievementsDAO;
import nl.zoetermeer.onszoetermeer.data.UserChallengesDAO;
import nl.zoetermeer.onszoetermeer.models.Achievement;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.UserAchievements;

public class AchievementTriggers
{
    private Application application;
    private int userId;
    private List<Challenge> mentalChallengesByUser;
    private List<Challenge> physicalChallengesByUser;
    private List<Achievement> availableAchievements;

    public AchievementTriggers(Application application, int userId) {
        this.application = application;
        this.userId = userId;

        new selectAchievementsAsync().execute();
    }

    private void checkAchievements() {

    }

    private class selectAchievementsAsync extends AsyncTask<Void,Void, Void>
    {
        private AchievementDAO achievementDAO;
        private UserAchievementsDAO userAchievementsDAO;
        private UserChallengesDAO userChallengesDAO;
        private DummyDatabase dummyDB;

        selectAchievementsAsync() {
            dummyDB = DummyDatabase.getDatabase(application);
            achievementDAO = dummyDB.achievementDAO();
            userAchievementsDAO = dummyDB.userAchievementsDAO();
            userChallengesDAO = dummyDB.userChallengesDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Get all available achievements
            availableAchievements = achievementDAO.getAllAchievements();
            //Get all the challenges completed by the user
            mentalChallengesByUser = userChallengesDAO.getMentalChallengesForUser(userId);
            physicalChallengesByUser = userChallengesDAO.getPhysicalChallengesForUser(userId);

            int vitalityMentalCount = mentalChallengesByUser.size();
            int vitalityPhysicalCount = physicalChallengesByUser.size();
            int vitalityTotalCount = vitalityMentalCount + vitalityPhysicalCount;



            return voids[0];
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("selectAchiev.Async ",availableAchievements.size()+" row(s) found.");
            checkAchievements();
        }

    }

    private class insertUserAchievementsAsync extends AsyncTask<UserAchievements, Void, Void>
    {
        private DummyDatabase dummyDB;
        private UserAchievementsDAO userAchievementsDAO;

        insertUserAchievementsAsync() {
            dummyDB = DummyDatabase.getDatabase(application);
            userAchievementsDAO = dummyDB.userAchievementsDAO();
        }

        @Override
        protected Void doInBackground(final UserAchievements... params) {
            userAchievementsDAO.insert(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("insertUserAchiev.Async", "UserAchievements row inserted.");
        }
    }
}