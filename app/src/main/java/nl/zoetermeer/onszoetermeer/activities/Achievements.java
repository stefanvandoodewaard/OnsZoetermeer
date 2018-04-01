package nl.zoetermeer.onszoetermeer.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.adapters.AchievementsAdapter;
import nl.zoetermeer.onszoetermeer.data.AchievementDAO;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserAchievementsDAO;
import nl.zoetermeer.onszoetermeer.models.Achievement;

public class Achievements extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Achievement> achievementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        int userId = pref.getInt("user_id", 0);

        drawToolbar();

        new selectAchievementsAsync(userId).execute();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.achievements_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new AchievementsAdapter(achievementsList);
        recyclerView.setAdapter(adapter);
    }

    private class selectAchievementsAsync extends AsyncTask<Void,Integer,List<Achievement>>
    {
//        private AchievementDAO achievementDAO;
        private UserAchievementsDAO userAchievementsDAO;
        private DummyDatabase dummyDB;
        private int userId;

        selectAchievementsAsync(int userId) {
            dummyDB = DummyDatabase.getDatabase(getApplication());
//            achievementDAO = dummyDB.achievementDAO();
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
