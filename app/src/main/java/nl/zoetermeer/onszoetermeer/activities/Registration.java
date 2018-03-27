package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.helpers.InputValidator;
import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.R;

public class Registration extends AppCompatActivity
{
    private InputValidator inputValidator;
    private User newUser;
    private EditText regEmail, regFname, regLname, regPw1, regPw2;
    private RadioGroup regGndr;
    private RadioButton regGndrMale, regGndrFemale;
    private registerUserAsync mAuthTask = null;
    boolean successfulValidationEmail = false;
    boolean successfulValidationFname = false;
    boolean successfulValidationLname = false;
    boolean successfulValidationGender = false;
    boolean successfulValidationPassword1 = false;
    boolean successfulValidationPassword2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.i("ACTIVITY:", "Registration created.");

//        dummyDB = DummyDatabase.getDatabase(getApplication());
//        userDAO = dummyDB.userDAO();

        inputValidator = new InputValidator();
        newUser = new User();

        regEmail = findViewById(R.id.reg_input_mail);
        regFname = findViewById(R.id.reg_input_fname);
        regLname = findViewById(R.id.reg_input_lname);
        regGndr = findViewById(R.id.gender);
        regGndrMale = findViewById(R.id.male);
        regGndrFemale = findViewById(R.id.female);
        regPw1 = findViewById(R.id.reg_input_pw1);
        regPw2 = findViewById(R.id.reg_input_pw2);

        addListeners();
    }


    public void validateRegistration(final View view) {

        if (mAuthTask != null)
        {
            Log.d("validateRegistration() ", "mAuthTask != null");
            return;
        }
        
        successfulValidationEmail = inputValidator.validateEmail(regEmail);
        successfulValidationFname = inputValidator.validateName(regFname);
        successfulValidationLname = inputValidator.validateName(regFname);
        successfulValidationPassword1 = inputValidator.validatePassword(regPw1);
        successfulValidationPassword2 = inputValidator.validatePassword(regPw2);

        String passwordOne = regPw1.getText().toString();
        String passwordTwo = regPw2.getText().toString();

        if (!successfulValidationEmail)
        {
            Log.d("validateRegistration() ", "Invalid email");
            regEmail.requestFocus();
            return;
        }

        if (!successfulValidationFname)
        {
            Log.d("validateRegistration() ", "Invalid first name");
            regFname.requestFocus();
            return;
        }

        if (!successfulValidationLname)
        {
            Log.d("validateRegistration() ", "Invalid last name");
            regLname.requestFocus();
            return;
        }

        if (!successfulValidationGender)
        {
            Log.d("validateRegistration() ", "No gender selected");
            regGndr.requestFocus();
            return;
        }

        if (!successfulValidationPassword1)
        {
            Log.d("validateRegistration() ", "Invalid password #1");
            regPw1.requestFocus();
            return;
        }

        if (!successfulValidationPassword2)
        {
            Log.d("validateRegistration() ", "Invalid password #2");
            regPw2.requestFocus();
            return;
        }

        if (!passwordOne.equals(passwordTwo))
        {
            Log.d("validateRegistration() ", "Password #1 & #2 don't match");
            regPw1.requestFocus();
            regPw1.setError("Wachtwoorden niet gelijk!");
            regPw2.setError("Wachtwoorden niet gelijk!");
            return;
        }

        regEmail.setError(null);
        regFname.setError(null);
        regLname.setError(null);
        regGndrMale.setError(null);
        regGndrFemale.setError(null);
        regFname.setError(null);
        regPw1.setError(null);
        regPw2.setError(null);

        newUser.setM_email(regEmail.getText().toString());
        newUser.setM_first_name(regFname.getText().toString());
        newUser.setM_last_name(regLname.getText().toString());
        // newUser.Gender is set by radioGroup listener
        newUser.setM_password(regPw2.getText().toString());
//        Date date = new Date();
        newUser.setM_last_active(new Date());

        String email = regEmail.getText().toString();
        mAuthTask = new registerUserAsync(email);
        mAuthTask.execute(newUser);

    }

    private class registerUserAsync extends AsyncTask<User, Void, Void>
    {
        private DummyDatabase dummyDB;
        private UserDAO userDao;
        private String email;
        private boolean succes;

        registerUserAsync(String verifyEmail) {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            userDao = dummyDB.userDAO();
            email = verifyEmail;
            succes = false;
        }

        @Override
        protected Void doInBackground(final User... params) {

            User verifyUser = dummyDB.userDAO().getByEmail(email);
            if (verifyUser != null) {
                Log.d("registerUserAsync ", "Email already exists!");
                return null;
            }

            userDao.insert(params[0]);
            Log.d("registerUserAsync ", "User row inserted.");
            succes = true;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAuthTask = null;
            if (!succes) {
                regEmail.setError("Email is al eerder gebruikt!");
                regEmail.requestFocus();
            } else {
                Toast.makeText(Registration.this, "Registratie voltooid.", Toast.LENGTH_LONG).show();

                Intent mainHomeScreenBinder = new Intent(Registration.this, Login.class);
                startActivity(mainHomeScreenBinder);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("registerUserAsync:", " onCancelled() called");
        }
    }

    private void addListeners() {

        regGndr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male: {
                        newUser.gender = User.Gender.Man;
                        regGndrMale.setError(null);
                        regGndrFemale.setError(null);
                        successfulValidationGender = true;
                        break;
                    }
                    case R.id.female: {
                        newUser.gender = User.Gender.Vrouw;
                        regGndrMale.setError(null);
                        regGndrFemale.setError(null);
                        successfulValidationGender = true;
                        break;
                    }
                }
            }
        });
    }
}
