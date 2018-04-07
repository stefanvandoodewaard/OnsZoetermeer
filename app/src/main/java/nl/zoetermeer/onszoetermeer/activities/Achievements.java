package nl.zoetermeer.onszoetermeer.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.AchievementsAdapter;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserAchievementsDAO;
import nl.zoetermeer.onszoetermeer.models.Achievement;

public class Achievements extends Base
{
    private List<Achievement> achievementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        Log.d("ACTIVITY:", "Achievements created.");

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        int userId = pref.getInt("user_id", 0);

        useToolbar();

        new selectAchievementsAsync(userId).execute();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.achievements_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        RecyclerView.Adapter adapter = new AchievementsAdapter(achievementsList, getApplicationContext());
        recyclerView.setAdapter(adapter);
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
}
