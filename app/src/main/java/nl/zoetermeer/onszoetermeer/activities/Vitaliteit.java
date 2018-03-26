package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.ChallengeDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.models.Challenge;

public class Vitaliteit extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private DummyDatabase dummyDB;
    private List<Challenge> challengeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitaliteit);
        Log.i("ACTIVITY:", "Vitaliteit created.");

        dummyDB = DummyDatabase.getDatabase(getApplication());



//
//        ArrayAdapter<Challenge> arrayAdapter =
//                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, challengesList);
//
//        challengesListView.setAdapter(arrayAdapter);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayShowTitleEnabled(false);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        Log.i("ACTIVITY:", "Home created.");

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        switch (view.getId()) {
            case R.id.vitaliteit_mentaal_button: {
                Log.i("BUTTON:", "Vitaliteit > Mentaal.");
                setContentView(R.layout.list_challenges);
                new SelectMentalAsyncTask(dummyDB).execute();

            }
            break;
            case R.id.vitaliteit_fysiek_button: {
                Log.i("BUTTON:", "Vitaliteit > Fysiek.");

            }
        }
    }

    private void setListAdapter() {

        ListView challengesListView = findViewById(R.id.challenges_list);

        ArrayAdapter<Challenge> adapter = new ArrayAdapter<Challenge>(this,
                android.R.layout.simple_list_item_1, challengeList);

        challengesListView.setAdapter(adapter);
    }

    private class SelectMentalAsyncTask extends AsyncTask<Void,Integer,List<Challenge>>
    {
        private ChallengeDAO challengeDAO;
        private List<Challenge> selectList;

        SelectMentalAsyncTask(DummyDatabase db) {
            challengeDAO = db.challengeDAO();
        }

        @Override
        protected List<Challenge> doInBackground(Void... voids) {
//            selectList = challengeDAO.getMentalChallenges();
            return challengeDAO.getMentalChallenges();
        }

        @Override
        protected void onPostExecute(List<Challenge> challenges) {
            super.onPostExecute(challenges);
            challengeList = challenges;
            setListAdapter();
            Log.d("ASYNC-SELECT: ",challenges.size()+" row(s) found.");




        }
    }


}
