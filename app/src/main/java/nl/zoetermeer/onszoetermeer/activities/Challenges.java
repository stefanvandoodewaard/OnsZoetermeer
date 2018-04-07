package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.ChallengesAdapter;
import nl.zoetermeer.onszoetermeer.data.ChallengeDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.helpers.RecyclerViewClickListener;
import nl.zoetermeer.onszoetermeer.helpers.RecyclerViewTouchListener;
import nl.zoetermeer.onszoetermeer.models.Challenge;

public class Challenges extends Base
{
    private List<Challenge> challengesList;
    private int type;
    private Bundle bundleDetails;
    private Intent challengeDetailsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        Bundle bundle = getIntent().getExtras();
        type = 0;
        if(bundle != null) {
            type = bundle.getInt("type");
        }
        Log.d("ACTIVITY:", "Challenges type " + type + " created.");

        bundleDetails = new Bundle();
        challengeDetailsIntent = new Intent(this, ChallengeDetails.class);

        new selectChallengesAsync().execute();

        useToolbar();
    }


    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.challenges_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new ChallengesAdapter(challengesList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                bundleDetails.putInt("challenge_id", challengesList.get(position).getId());
                challengeDetailsIntent.putExtras(bundleDetails);
                startActivity(challengeDetailsIntent);
            }
        }));
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
}
