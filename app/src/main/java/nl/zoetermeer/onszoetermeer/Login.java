package nl.zoetermeer.onszoetermeer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Models.User;


public class Login extends AppCompatActivity
{
    private DummyDatabase dummyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        dummyDB = DummyDatabase.getDatabase(getApplicationContext());
        dummyDB.createData(getApplicationContext());

        //check of gebruiker is aangemaakt
        User user = dummyDB.userDAO().getByName("Jannie").get(0);
        Toast.makeText(this, String.valueOf("Gebruiker " + user.getM_first_name() + " aangemaakt"), Toast.LENGTH_LONG).show();

    }

    public void sendMessage(View view) {
        Intent mainHomeScreenBinder = new Intent(this, Home.class);
        startActivity(mainHomeScreenBinder);
    }

}