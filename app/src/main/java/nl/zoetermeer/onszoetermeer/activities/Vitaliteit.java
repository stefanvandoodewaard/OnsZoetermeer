package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class Vitaliteit extends Base
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitaliteit);
        Log.i("ACTIVITY:", "Vitaliteit created.");

        useToolbar();
    }

    public void sendMessage(View view)
    {
        Bundle bundle = new Bundle();
        Intent challengesIntent = new Intent(this, Challenges.class);
        switch (view.getId())
        {
            case R.id.vitaliteit_mentaal_button:
            {
                Log.i("BUTTON:", "Vitaliteit > Mentaal.");

                bundle.putInt("type", 1);
                challengesIntent.putExtras(bundle);
                startActivity(challengesIntent);
            }
            break;
            case R.id.vitaliteit_fysiek_button:
            {
                Log.i("BUTTON:", "Vitaliteit > Fysiek.");

                bundle.putInt("type", 2);
                challengesIntent.putExtras(bundle);
                startActivity(challengesIntent);
            }
        }
    }
}
