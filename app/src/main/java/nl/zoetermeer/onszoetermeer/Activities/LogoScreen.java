package nl.zoetermeer.onszoetermeer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import nl.zoetermeer.onszoetermeer.R;

public class LogoScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoscreen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        }, 5000);
    }
}










