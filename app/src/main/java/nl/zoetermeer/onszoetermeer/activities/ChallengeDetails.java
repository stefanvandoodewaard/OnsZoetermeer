package nl.zoetermeer.onszoetermeer.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.ChallengeDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserChallengesDAO;
import nl.zoetermeer.onszoetermeer.helpers.AchievementTriggers;
import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.UserChallenges;

public class ChallengeDetails extends Base
{
    private DrawerLayout mDrawerLayout;
    private int challengeId, userId;
    private TextView challengeName, challengeDetails;
    private boolean challengeCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);
        Log.i("ACTIVITY: ", "ChallengeDetails created.");

        Bundle bundleDetails = getIntent().getExtras();
        if(bundleDetails != null) {
            challengeId = bundleDetails.getInt("challenge_id");
            Log.i("ACTIVITY: ", "ChallengeDetails succesfully created, challengeId = " + challengeId);
        } else {
            Log.e("Challengedetails", "Oncreate() no challenge ID passed");
            finish();
        }


        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        userId = pref.getInt("user_id", 0);

        challengeName = findViewById(R.id.challenge_details_name);
        challengeDetails = findViewById(R.id.challenge_details_details);
        Switch challengeSwitch = findViewById(R.id.switch_challenge);
        challengeCompleted = false;

        useToolbar();

        new selectChallengeDetailsAsync(challengeId).execute();

        challengeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    challengeCompleted = true;
                    Log.i("challengeSwitch", "true");
                } else {
                    challengeCompleted = false;
                    Log.i("challengeSwitch", "false");
                }
            }
        });
    }

    public void onClick(View view) {
        if (challengeCompleted) {
            UserChallenges userChallenge = new UserChallenges(userId, challengeId, new Date());
            new insertUserChallengeAsync(userId).execute(userChallenge);
            return;
        }
        finish();
    }

    private class insertUserChallengeAsync extends AsyncTask<UserChallenges, Void, Void>
    {
        private DummyDatabase dummyDB;
        private UserChallengesDAO userChallengesDAO;
        private int userId;

        insertUserChallengeAsync(int userId) {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            userChallengesDAO = dummyDB.userChallengesDAO();
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(final UserChallenges... params) {
            userChallengesDAO.insert(params[0]);

            //Pass all completed challenges to achievement trigger class.
//            List<Challenge> challenges = userChallengesDAO.getChallengesForUser(userId);
            new AchievementTriggers(getApplication(), userId);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("insertUserChalengeAsync", "UserChallenges row inserted.");
            finish();
        }
    }

    private class selectChallengeDetailsAsync extends AsyncTask<Void,Integer,Challenge>
    {
        private ChallengeDAO challengeDAO;
        private DummyDatabase dummyDB;
        private int id;

        selectChallengeDetailsAsync(int id) {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            challengeDAO = dummyDB.challengeDAO();
            this.id = id;
        }

        @Override
        protected Challenge doInBackground(Void... voids) {

            return challengeDAO.getByID(id);
        }

        @Override
        protected void onPostExecute(Challenge challenge) {
            super.onPostExecute(challenge);
            challengeName.setText(challenge.getName());

            if (challenge.vitalityType == Challenge.VitalityType.Fysiek) {
                challengeDetails.setText(challenge.getDetails());
            }

            Log.d("ASYNC-SELECT: ","Challenge: " + challenge.getName()+" found.");
        }
    }
}
