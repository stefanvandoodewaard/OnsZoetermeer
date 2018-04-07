package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
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
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class Help extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Log.i("ACTIVITY:", "Help created.");

        drawToolbar();
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
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
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
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Bundle bundle = new Bundle();
        Intent requestDetailsIntent = new Intent(this, RequestDetails.class);
        switch (view.getId()) {
            case R.id.help_meal_button: {
                Log.i("BUTTON:", "Help > Meal.");

                bundle.putInt("request_type", 0);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
            case R.id.help_shop_button: {
                Log.i("BUTTON:", "Help > Shop.");

                bundle.putInt("request_type", 1);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
            case R.id.help_chore_button: {
                Log.i("BUTTON:", "Help > Chore.");

                bundle.putInt("request_type", 2);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
            case R.id.help_transport_button: {
                Log.i("BUTTON:", "Help > Transport.");

                bundle.putInt("request_type", 3);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;

        }
    }
}
