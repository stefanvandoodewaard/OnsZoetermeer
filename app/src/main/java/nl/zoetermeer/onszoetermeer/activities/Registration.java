package nl.zoetermeer.onszoetermeer.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;
import java.util.List;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.helpers.InputValidator;
import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.R;

public class Registration extends AppCompatActivity
{
    private DummyDatabase dummyDB;
    private UserDAO userDAO;
    private InputValidator inputValidator;
    private User newUser;
    private EditText regEmail, regFname, regLname, regPw1, regPw2;
    private RadioGroup regGndr;
    private RadioButton regGndrMale, regGndrFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.i("ACTIVITY:", "Registration created.");

        dummyDB = DummyDatabase.getDatabase(getApplication());
        userDAO = dummyDB.userDAO();

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


                createUser(view);

    }

    private void createUser(View view) {

        newUser.setM_email(regEmail.getText().toString());
        newUser.setM_first_name(regFname.getText().toString());
        newUser.setM_last_name(regLname.getText().toString());
        // User.Gender is set by radioGroup listener

        // TO-DO get password from validator
        newUser.setM_password(regPw1.getText().toString());

        Date date = new Date();
        newUser.setM_last_active(date);

        new insertAsyncTask(dummyDB).execute(newUser);
    }


    private class insertAsyncTask extends AsyncTask<User, Void, Void>
    {
        private UserDAO userDao;
        private List<User> users;

        insertAsyncTask(DummyDatabase db) {
            userDao = db.userDAO();
        }

        @Override
        protected Void doInBackground(final User... params) {
            userDao.insert(params[0]);
            users = dummyDB.userDAO().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("insertAsyncTask:", "User row inserted.");
            Log.i("Select:",users.size()+" User row(s) found");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("insertAsyncTask:", " onCancelled() called");
        }
    }

    private void addListeners() {
        regEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    inputValidator.validateEmail(regEmail);
                }
            }
        });

        regFname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    inputValidator.validateName(regFname);
                }
            }
        });

        regLname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    inputValidator.validateName(regLname);
                }
            }
        });

        regGndr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male: {
                        newUser.gender = User.Gender.Man;
                        break;
                    }
                    case R.id.female: {
                        newUser.gender = User.Gender.Vrouw;
                        break;
                    }
                }
            }
        });

        regGndrFemale.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (newUser.gender == null) {
                        regGndrMale.setError("Selecteer een optie!");
                        regGndrFemale.setError("Selecteer een optie!");

                    } else {
                        regGndrMale.setError(null);
                        regGndrFemale.setError(null);
                    }
                }
            }
        });

        regPw1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    inputValidator.validateNotNull(regPw1);
                }
            }
        });

        regPw2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    inputValidator.validateNotNull(regPw2);
                }
            }
        });
    }
}
