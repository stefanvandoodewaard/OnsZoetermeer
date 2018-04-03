package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.MessageFormat;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.models.User;

public class Home extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    int pStatusMentaal = 0;
    int pStatusFysiek = 0;
    private double progressUserMental, progressUserPhysical;
    TextView percentageMentaal, percentageFysiek;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Log.i("ACTIVITY:", "Home created.");

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        int userId = pref.getInt("user_id", 0);

        drawToolbar();

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

    private void drawToolbar()
    {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayShowTitleEnabled(false);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()

                {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                    {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId())
                        {
                            case R.id.nav_home:
                                NavUtils.navigateUpFromSameTask(this);
                                Log.i("MENU ITEM:", "Home > Home.");
                                return true;
                        }
                        return Home.super.onOptionsItemSelected(menuItem);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            case R.id.hulp_button:
            {
//                Intent messageContact = new Intent(this, Hulp.class);
//                startActivity(messageContact);
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
                setContentView(R.layout.activity_home_screen);
            }
        }
    }
}