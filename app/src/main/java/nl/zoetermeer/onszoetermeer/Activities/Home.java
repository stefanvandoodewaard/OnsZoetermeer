package nl.zoetermeer.onszoetermeer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class Home extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void sendMessage(View view)
    {
        switch (view.getId())
        {
            case R.id.contact_button:
            {
                Intent messageContact = new Intent(this, Contact.class);
                startActivity(messageContact);
            }
            break;
            case R.id.vitaliteit_button:
            {
                Intent messageVitaliteit = new Intent(this, Vitaliteit.class);
                startActivity(messageVitaliteit);
            }
            break;
            default:
            {
                setContentView(R.layout.activity_home_screen);
            }
        }
    }
}