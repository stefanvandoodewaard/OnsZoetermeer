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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.RequestDAO;
import nl.zoetermeer.onszoetermeer.models.Request;

public class RequestDetails extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        Log.i("ACTIVITY: ", "RequestDetails created.");

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        userId = pref.getInt("user_id", 0);

        drawToolbar();
    }

    private class insertRequestAsync extends AsyncTask<Request, Void, Void>
    {
        private DummyDatabase dummyDB;
        private RequestDAO requestDAO;
        private int userId;

        insertRequestAsync(int userId) {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            requestDAO = dummyDB.requestDAO();
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(final Request... params) {
            requestDAO.insert(params[0]);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("insertRequestAsync", "Request row inserted.");
            finish();
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
