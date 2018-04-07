package nl.zoetermeer.onszoetermeer.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.Objects;

import nl.zoetermeer.onszoetermeer.R;

public class Base extends AppCompatActivity
{
    private DrawerLayout fullView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base,
                null);
        FrameLayout activityContainer = fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);

        if (useToolbar())
        {
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
                            fullView.closeDrawers();

                            menuItemSelector(menuItem);

                            return true;
                        }
                    });
        }
        else
        {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_back);
            actionbar.setDisplayShowTitleEnabled(true);
        }
    }

    public boolean useToolbar()
    {
        return true;
    }

    public void menuItemSelector(@NonNull MenuItem menuItem)
    {
        Intent intent;

        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
            {
                Log.i("BUTTON:", "Activity > Home.");

                intent = new Intent(this, Home.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_contact:
            {
                Log.i("BUTTON:", "Activity > Contact.");

                intent = new Intent(this, Contact.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_vitaliteit:
            {
                Log.i("BUTTON:", "Activity > Vitaliteit.");
                intent = new Intent(this, Vitaliteit.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_hulp:
            {
                Log.i("BUTTON:", "Activity > Hulp.");
                intent = new Intent(this, Hulp.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_achievement:
            {
                Log.i("BUTTON:", "Activity > Achievements.");
                intent = new Intent(this, Achievements.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_uitloggen:
            {
                Log.i("BUTTON:", "Activity > Uitloggen.");

                SharedPreferences.Editor editor = getPreferences(0).edit();
                editor.clear();
                editor.apply();
                finish();

                intent = new Intent(this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                if(useToolbar())
                {
                    Log.i("BUTTON:", "Icon > Menu.");
                    fullView.openDrawer(GravityCompat.START);
                    return true;
                }
                else
                {
                    finish();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
