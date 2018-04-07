package nl.zoetermeer.onszoetermeer.activities;

import android.os.Bundle;
import android.util.Log;

import nl.zoetermeer.onszoetermeer.R;

public class Hulp extends Base
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hulp);
        Log.i("ACTIVITY:", "Hulp created.");

        useToolbar();
    }

}
