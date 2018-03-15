package nl.zoetermeer.onszoetermeer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class Vitaliteit extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitaliteit);
        Log.i("ACTIVITY:", "Vitaliteit created.");

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
//                Intent messageContact = new Intent(this, Contact.class);
//                startActivity(messageContact);
            }
            break;
            case R.id.vitaliteit_fysiek_button: {
                Log.i("BUTTON:", "Vitaliteit > Fysiek.");
//                Intent messageVitaliteit = new Intent(this, Vitaliteit.class);
//                startActivity(messageVitaliteit);
            }
        }
    }

}
