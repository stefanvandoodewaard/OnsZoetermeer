package nl.zoetermeer.onszoetermeer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.R;

public class Registration extends AppCompatActivity
{
    private DummyDatabase dummyDB;
    private User newUser = new User();
    private boolean genderStatus = false;
    private boolean validationStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.i("ACTIVITY:", "Registration created.");

        dummyDB = DummyDatabase.getDatabase(getApplicationContext());


        checkGender();

    }

    public void checkGender() {

        final RadioGroup radioGroup = findViewById(R.id.gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male: {
                        newUser.gender = User.Gender.Man;
                        genderStatus = true;
                        break;
                    }
                    case R.id.female: {
                        newUser.gender = User.Gender.Vrouw;
                        genderStatus = true;
                        break;
                    }
                    default: {
                        genderStatus = false;
                    }
                }
            }
        });
    }

    public void validateRegistration(View view) {

        //TO-DO validation code

        createUser(view);
    }

    public void createUser(View view) {

        EditText reg_email = findViewById(R.id.reg_input_mail);
        EditText reg_fname = findViewById(R.id.reg_input_fname);
        EditText reg_lname = findViewById(R.id.reg_input_lname);

        EditText reg_pswrd = findViewById(R.id.reg_input_pw1);

        newUser.setM_email(reg_email.getText().toString());
        newUser.setM_first_name(reg_fname.getText().toString());
        newUser.setM_last_name(reg_lname.getText().toString());
        newUser.setM_password(reg_pswrd.getText().toString());

        Date date = new Date();
        newUser.setM_last_active(date);

        dummyDB.userDAO().insert(newUser);

        Log.i("DATABASE:", "New User created");

        //check of gebruiker is aangemaakt
        User user = dummyDB.userDAO().getByName(reg_fname.getText().toString()).get(0);
        Toast.makeText(this, String.valueOf("Gebruiker " + user.getM_first_name() + " aangemaakt"), Toast.LENGTH_LONG).show();
    }
}
