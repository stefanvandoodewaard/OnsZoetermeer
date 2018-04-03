package nl.zoetermeer.onszoetermeer.helpers;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;
import java.util.List;

import nl.zoetermeer.onszoetermeer.data.AchievementDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserAchievementsDAO;
import nl.zoetermeer.onszoetermeer.data.UserChallengesDAO;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.UserAchievements;

    public class AchievementTrigger extends AsyncTask<Void,Void, Void>
    {
        private Application application;
        private int userId;
        private UserAchievementsDAO userAchievementsDAO;
        private UserChallengesDAO userChallengesDAO;

        public AchievementTrigger(Application application, int userId) {
            this.application = application;
            this.userId = userId;
            DummyDatabase dummyDB = DummyDatabase.getDatabase(application);
            userAchievementsDAO = dummyDB.userAchievementsDAO();
            userChallengesDAO = dummyDB.userChallengesDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Get all the challenges completed by the user
            List<Challenge> mentalChallengesByUser = userChallengesDAO.getMentalChallengesForUser(userId);
            List<Challenge> physicalChallengesByUser = userChallengesDAO.getPhysicalChallengesForUser(userId);

            int vitalityMentalCount = mentalChallengesByUser.size();
            int vitalityPhysicalCount = physicalChallengesByUser.size();
            int vitalityTotalCount = vitalityMentalCount + vitalityPhysicalCount;
            Log.i("UserChallenges:", "MENT = " + vitalityMentalCount + " PHYS = " + vitalityPhysicalCount + " TOTAL = " + vitalityTotalCount);

//            achievement 1 - "50 Mentale Uitdagingen" - Goud
//            achievement 2 - "25 Mentale Uitdagingen" - Zilver
//            achievement 3 - "10 Mentale Uitdagingen" - Brons
//            achievement 4 - "50 Fysieke Uitdagingen" - Goud
//            achievement 5 - "25 Fysieke Uitdagingen" - Zilver
//            achievement 6 - "10 Fysieke Uitdagingen" - Brons

            if (vitalityPhysicalCount == 10) {
                userAchievementsDAO.insert(new UserAchievements(userId, 6, new Date()));
            } else if (vitalityPhysicalCount == 25) {
                userAchievementsDAO.insert(new UserAchievements(userId, 5, new Date()));
            } else if (vitalityPhysicalCount == 50) {
                userAchievementsDAO.insert(new UserAchievements(userId, 4, new Date()));
            }
            if (vitalityMentalCount == 10) {
                userAchievementsDAO.insert(new UserAchievements(userId, 3, new Date()));
            } else if (vitalityMentalCount == 25) {
                userAchievementsDAO.insert(new UserAchievements(userId, 2, new Date()));
            } else if (vitalityMentalCount == 50) {
                userAchievementsDAO.insert(new UserAchievements(userId, 1, new Date()));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {



        }

    }
