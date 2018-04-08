package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.ProfileAchievementsAdapter;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserAchievementsDAO;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.models.Achievement;
import nl.zoetermeer.onszoetermeer.models.Request;
import nl.zoetermeer.onszoetermeer.models.User;

public class Profile extends Base
{
    private Bundle bundleDetails;
    private List<Achievement> achievementsList;
    private List<Request> requestsList;
    private int userId;
    private User profileUser;
    int pStatusMentaal = 0;
    int pStatusFysiek = 0;
    private double progressUserMental, progressUserPhysical;
    TextView percentageMentaal, percentageFysiek, profileName;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d("ACTIVITY:", "Profile created.");

        Bundle bundleDetails = getIntent().getExtras();
        userId = bundleDetails.getInt("user_id", 0);


        profileName = findViewById(R.id.profile_user_name);


        new selectUserInfoAsync(userId).execute();
        new selectAchievementsAsync(userId).execute();
    }

    public void sendMessage(View view)
    {
        switch (view.getId())
        {
            case R.id.chat_button:
            {
                Log.i("BUTTON:", "Profile > Chat.");
                Intent messageChat = new Intent(this, Chat.class);
                startActivity(messageChat);
            }
            break;
        }
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.profile_recyclerview_achievements);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerView.Adapter adapter = new ProfileAchievementsAdapter(achievementsList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        if (achievementsList.size() == 0) {
            TextView emptyAchievements = findViewById(R.id.achievements_empty_text_profile);
            emptyAchievements.setVisibility(View.VISIBLE);
        }
//        if (requestsList.size() == 0) {
//            TextView emptyRequests = findViewById(R.id.requests_empty_text_profile);
//            emptyRequests.setVisibility(View.VISIBLE);
//        }
        TextView emptyRequests = findViewById(R.id.requests_empty_text_profile);
        emptyRequests.setVisibility(View.VISIBLE);
    }


    private class selectAchievementsAsync extends AsyncTask<Void,Integer,List<Achievement>>
    {
        private UserAchievementsDAO userAchievementsDAO;
        private DummyDatabase dummyDB;
        private int userId;

        selectAchievementsAsync(int userId) {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            userAchievementsDAO = dummyDB.userAchievementsDAO();
            this.userId = userId;
        }

        @Override
        protected List<Achievement> doInBackground(Void... voids) {

            return userAchievementsDAO.getAchievementsForUser(userId);
        }

        @Override
        protected void onPostExecute(List<Achievement> achievements) {
            super.onPostExecute(achievements);
            achievementsList = achievements;
            setRecyclerView();
            Log.d("ASYNC-SELECT: ",achievements.size()+" row(s) found.");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        new selectUserInfoAsync(userId).execute();
        new selectAchievementsAsync(userId).execute();
    }


    private class selectUserInfoAsync extends AsyncTask<Void, Integer, User>
    {
        private UserDAO userDAO;
        private DummyDatabase dummyDB;
        private int userId;

        selectUserInfoAsync(int userId)
        {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            userDAO = dummyDB.userDAO();
            this.userId = userId;
        }

        @Override
        protected User doInBackground(Void... voids)
        {
            return userDAO.getByID(userId);
        }

        @Override
        protected void onPostExecute(User user)
        {
            super.onPostExecute(user);
            progressUserMental = user.getM_vit_ment();
            progressUserPhysical = user.getM_vit_phys();
            drawMentalProgress();
            drawPhysicalProgress();

            String firstName = user.getM_first_name();
            String lastName = user.getM_last_name();
            String fullName = firstName + " " + lastName;
            profileName.setText(fullName);

            Log.d("ASYNC-SELECT: ", "User info successfully loaded.");
        }
    }

    private void drawMentalProgress()
    {
        Drawable drawableMental = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_progressbarstyle, null);
        final ProgressBar mProgressMentaal = findViewById(R.id.circularProgressbarMentalProfile);
        mProgressMentaal.setProgress(100);
        mProgressMentaal.setSecondaryProgress(100);
        mProgressMentaal.setMax(100);
        mProgressMentaal.setProgressDrawable(drawableMental);
        percentageMentaal = findViewById(R.id.percentageMentaalProfile);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (pStatusMentaal < progressUserMental)
                {
                    pStatusMentaal += 1;
                    handler.post(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            mProgressMentaal.setProgress(pStatusMentaal);
                            percentageMentaal.setText(MessageFormat.format("{0}%",
                                    pStatusMentaal));
                        }
                    });
                    try
                    {
                        Thread.sleep(25);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void drawPhysicalProgress()
    {
        Drawable drawablePhysical = ResourcesCompat.getDrawable(getResources(), R.drawable.profile_progressbarstyle, null);
        final ProgressBar progressFysiek = findViewById(R.id.circularProgressbarPhysicalProfile);
        progressFysiek.setProgress(100);
        progressFysiek.setSecondaryProgress(100);
        progressFysiek.setMax(100);
        progressFysiek.setProgressDrawable(drawablePhysical);
        percentageFysiek = findViewById(R.id.percentageFysiekProfile);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (pStatusFysiek < progressUserPhysical)
                {
                    pStatusFysiek += 1;
                    handler.post(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            progressFysiek.setProgress(pStatusFysiek);
                            percentageFysiek.setText(MessageFormat.format("{0}%",
                                    pStatusFysiek));
                        }
                    });
                    try
                    {
                        Thread.sleep(25);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
