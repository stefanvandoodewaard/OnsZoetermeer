package nl.zoetermeer.onszoetermeer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import nl.zoetermeer.onszoetermeer.R;

public class Vitaliteit extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitaliteit);
        Log.i("ACTIVITY:", "Vitaliteit created.");
    }
}
