package nl.zoetermeer.onszoetermeer;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Models.User;


public class LoginScreen extends AppCompatActivity
{
    private DummyDatabase dummyDB;
    private User user1 = new User();
    private User user2 = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //Dummy database aanmaken
        dummyDB = Room.databaseBuilder(getApplicationContext(),
                DummyDatabase.class, "DUMMY_DATABASE").allowMainThreadQueries().build();
        //testdata aanmaken
        createData();
    }

    public void sendMessage(View view) {
        Intent mainHomeScreenBinder = new Intent(this, Home.class);
        startActivity(mainHomeScreenBinder);
    }

    private void createData() {
//        dummyDB.userDAO().deleteAll();
//        List<User> users = dummyDB.userDAO().getAll();
//        if (users.size() == 0) {

            user1.setM_email("tante_jannie@casema.nl");
            user1.setM_password("password");
            user1.setM_gender("Female");
            user1.setM_first_name("Jannie");
            user1.setM_last_name("Jansen");
            dummyDB.userDAO().insert(user1);

            Toast.makeText(this, "Users created.", Toast.LENGTH_SHORT).show();

//            user2 = dummyDB.userDAO().getByID(0);
//            Toast.makeText(this, String.valueOf(user2.getM_first_name()), Toast.LENGTH_LONG).show();


//        } else {
//            Toast.makeText(this, "Stuff? happend", Toast.LENGTH_SHORT).show();
//        }
    }
}