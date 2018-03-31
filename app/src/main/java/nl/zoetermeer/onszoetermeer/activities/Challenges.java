package nl.zoetermeer.onszoetermeer.activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.ChallengesAdapter;
import nl.zoetermeer.onszoetermeer.data.ChallengeDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.models.Challenge;

public class Challenges extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Challenge> challengesList;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        Bundle bundle = getIntent().getExtras();
        type = 0; // or other values
        if(bundle != null)
            type = bundle.getInt("type");
        Log.d("ACTIVITY:", "Challenges + type " + type + " created.");


        new selectChallengesAsync().execute();


        drawToolbar();
    }


    private void setRecyclerView() {
        recyclerView = findViewById(R.id.challenges_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChallengesAdapter(challengesList);
        recyclerView.setAdapter(adapter);
    }

    private class selectChallengesAsync extends AsyncTask<Void,Integer,List<Challenge>>
    {
        private ChallengeDAO challengeDAO;
        private DummyDatabase dummyDB;

        selectChallengesAsync() {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            challengeDAO = dummyDB.challengeDAO();
        }

        @Override
        protected List<Challenge> doInBackground(Void... voids) {
            List<Challenge> temp = null;
            if (type == 0) {
                Log.d("selectChallengesAsync", "doInBackground() return null");
                return null;
            } else if (type == 1) {
                temp = challengeDAO.getMentalChallenges();
            } else if (type == 2) {
                temp =  challengeDAO.getPhysicalChallenges();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(List<Challenge> challenges) {
            super.onPostExecute(challenges);
            challengesList = challenges;
            setRecyclerView();
            Log.d("ASYNC-SELECT: ",challenges.size()+" row(s) found.");
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
