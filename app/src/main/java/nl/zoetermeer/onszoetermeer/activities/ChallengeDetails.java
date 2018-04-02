package nl.zoetermeer.onszoetermeer.activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.ChallengeDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.models.Challenge;

public class ChallengeDetails extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private int id;
    private TextView challengeName, challengeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);
        Log.i("ACTIVITY: ", "ChallengeDetails created.");

        Bundle bundleDetails = getIntent().getExtras();
        if(bundleDetails != null) {
            id = bundleDetails.getInt("id");
            Toast.makeText(getApplicationContext(), "ChallengeDetails started with id: " + id, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("Challengedetails", "Oncreate() no challenge ID passed");
        }

        challengeName = findViewById(R.id.challenge_details_name);
        challengeDetails = findViewById(R.id.challenge_details_details);

        drawToolbar();

        new selectChallengeDetailsAsync(id).execute();
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
            challengeDetails.setText(challenge.getDetails());

            Log.d("ASYNC-SELECT: ","Challenge: " + challenge.getName()+" found.");
        }
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

                        return true;
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
}
