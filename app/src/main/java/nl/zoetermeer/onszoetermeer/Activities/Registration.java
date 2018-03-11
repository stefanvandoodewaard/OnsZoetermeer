package nl.zoetermeer.onszoetermeer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Helpers.InputValidator;
import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.R;

public class Registration extends AppCompatActivity
{
    private DummyDatabase dummyDB;
    private InputValidator inputValidator;
    private User newUser;
    private boolean genderStatus;
    private boolean validationStatus;
    private EditText regEmail, regFname, regLname, regPw1, regPw2;
    private RadioGroup regGndr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.i("ACTIVITY:", "Registration created.");

        dummyDB = DummyDatabase.getDatabase(getApplicationContext());
        inputValidator = new InputValidator();
        newUser = new User();
        genderStatus = false;
        validationStatus = false;

        regEmail = findViewById(R.id.reg_input_mail);
        regFname = findViewById(R.id.reg_input_fname);
        regLname = findViewById(R.id.reg_input_lname);
        regGndr = findViewById(R.id.gender);
        regPw1 = findViewById(R.id.reg_input_pw1);
        regPw2 = findViewById(R.id.reg_input_pw2);

        addListeners();
        checkGender();

    }


    public void addListeners() {

        regEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputValidator.validateEmail(regEmail);
            }
        });

        regFname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputValidator.validateName(regFname);
            }
        });

        regLname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputValidator.validateName(regLname);
            }
        });
    }

    public void checkGender() {

        regGndr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        //TO-DO check validator statusses

        createUser(view);
    }

    public void createUser(View view) {


        newUser.setM_email(regEmail.getText().toString());
        newUser.setM_first_name(regFname.getText().toString());
        newUser.setM_last_name(regLname.getText().toString());

        // TO-DO get password from validator
//        newUser.setM_password(regPw1.getText().toString());

        Date date = new Date();
        newUser.setM_last_active(date);

        dummyDB.userDAO().insert(newUser);

        Log.i("DATABASE:", "New User created");

        //check of gebruiker is aangemaakt
        User user = dummyDB.userDAO().getByName(regFname.getText().toString()).get(0);
        Toast.makeText(this, String.valueOf("Gebruiker " + user.getM_first_name() + " aangemaakt"), Toast.LENGTH_LONG).show();
    }
}
