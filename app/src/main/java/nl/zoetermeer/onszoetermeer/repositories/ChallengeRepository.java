package nl.zoetermeer.onszoetermeer.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.data.ChallengeDAO;



public class ChallengeRepository
{
    private ChallengeDAO challengeDAO;

    public ChallengeRepository(Application application) {
        DummyDatabase dummyDB = DummyDatabase.getDatabase(application);
        challengeDAO = dummyDB.challengeDAO();

        createTestChallenges(dummyDB);


    }

    //Insert Challenge
    public void insert(Challenge challenge) {
        new insertAsyncTask(challengeDAO).execute(challenge);
    }

    private static class insertAsyncTask extends AsyncTask<Challenge, Void, Void>
    {

        private ChallengeDAO mAsyncTaskDao;

        insertAsyncTask(ChallengeDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Challenge... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }


    }


    //Get Mental Challenges
    public List<Challenge> getMentalChallenges() {

        mentalChallengesAsync task = new mentalChallengesAsync(challengeDAO);
        task.execute();
        List<Challenge> result = task.doInBackground();

        return result;
    }

    private class mentalChallengesAsync extends AsyncTask<Void, String, List<Challenge>>
    {

        private ChallengeDAO mAsyncTaskDao;

        mentalChallengesAsync(ChallengeDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Challenge> doInBackground(Void... params) {
            List<Challenge> result = null;
            try {
                result = mAsyncTaskDao.getMentalChallenges();
            } catch (Exception e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Challenge> result) {
            super.onPostExecute(result);

        }
    }


    // Create test-data
    public void createTestChallenges(DummyDatabase dummyDB) {
        TestChallengesAsync task = new TestChallengesAsync(dummyDB);
        task.execute();
    }

    private static class TestChallengesAsync extends AsyncTask<Void, Void, Void>
    {

        private final ChallengeDAO challengeDAO;

        TestChallengesAsync(DummyDatabase dummyDB) {
            challengeDAO = dummyDB.challengeDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {
                challengeDAO.deleteAll();
                Challenge challenge1 = new Challenge();
                challenge1.setName("Challenge 1");
                challenge1.vitalityType = Challenge.VitalityType.Mentaal;
                challengeDAO.insert(challenge1);
                Challenge challenge2 = new Challenge();
                challenge2.setName("Challenge 2");
                challenge2.vitalityType = Challenge.VitalityType.Fysiek;
                challengeDAO.insert(challenge2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("REPOSITORY:", "Test data (re)created.");
        }
    }
}
