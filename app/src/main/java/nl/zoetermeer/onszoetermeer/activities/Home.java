package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.MessageFormat;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.models.User;

public class Home extends Base
{

    private int userId;
    int pStatusMentaal = 0;
    int pStatusFysiek = 0;
    private double progressUserMental, progressUserPhysical;
    TextView percentageMentaal, percentageFysiek;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i("ACTIVITY:", "Home created.");

        SharedPreferences mUserPreferences = getSharedPreferences("user_details",
                MODE_PRIVATE);
        userId = mUserPreferences.getInt("user_id", 0);

        useToolbar();

        new selectUserVitalitysAsync(userId).execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        new selectUserVitalitysAsync(userId).execute();
    }


    private class selectUserVitalitysAsync extends AsyncTask<Void, Integer, User>
    {
        private UserDAO userDAO;
        private DummyDatabase dummyDB;
        private int userId;

        selectUserVitalitysAsync(int userId)
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
            Log.d("ASYNC-SELECT: ", "User vitality stats successfully loaded.");
        }
    }

    private void drawMentalProgress()
    {
        Drawable drawableMental = ResourcesCompat.getDrawable(getResources(), R.drawable.progressbarstyle, null);
        final ProgressBar mProgressMentaal = findViewById(R.id.circularProgressbarMental);
        mProgressMentaal.setProgress(100);
        mProgressMentaal.setSecondaryProgress(100);
        mProgressMentaal.setMax(100);
        mProgressMentaal.setProgressDrawable(drawableMental);
        percentageMentaal = findViewById(R.id.percentageMentaal);

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
        Drawable drawablePhysical = ResourcesCompat.getDrawable(getResources(), R.drawable.progressbarstyle, null);
        final ProgressBar progressFysiek = findViewById(R.id.circularProgressbarPhysical);
        progressFysiek.setProgress(100);
        progressFysiek.setSecondaryProgress(100);
        progressFysiek.setMax(100);
        progressFysiek.setProgressDrawable(drawablePhysical);
        percentageFysiek = findViewById(R.id.percentageFysiek);

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

    public void sendMessage(View view)
    {
        switch (view.getId())
        {
            case R.id.contact_button:
            {
                Log.i("BUTTON:", "Home > Contact.");
                Intent messageContact = new Intent(this, Contact.class);
                startActivity(messageContact);
            }
            break;
            case R.id.vitaliteit_button:
            {
                Log.i("BUTTON:", "Home > Vitaliteit.");
                Intent messageVitaliteit = new Intent(this, Vitaliteit.class);
                startActivity(messageVitaliteit);
            }
            break;
            case R.id.hulp_button:
            {
                Log.i("BUTTON:", "Home > Help.");
                Intent messageHelp = new Intent(this, Help.class);
                startActivity(messageHelp);
            }
            break;
            case R.id.achievement_button:
            {
                Log.i("BUTTON:", "Home > Achievements.");
                Intent messageAchievements = new Intent(this, Achievements.class);
                startActivity(messageAchievements);
            }
            break;
            default:
            {
                setContentView(R.layout.activity_home);
            }
        }
    }
}