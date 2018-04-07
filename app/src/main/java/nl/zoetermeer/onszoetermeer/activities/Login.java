package nl.zoetermeer.onszoetermeer.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.helpers.InputValidator;
import nl.zoetermeer.onszoetermeer.models.User;

public class Login extends AppCompatActivity
{
    private InputValidator inputValidator;
    private EditText mEmailView, mPasswordView;
    private ProgressDialog mProgress;
    private SharedPreferences pref;
    boolean successfulValidationPassword = false;
    boolean successfulValidationEmail = false;
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Log.i("ACTIVITY:", "Login created.");


        inputValidator = new InputValidator();

        pref = getSharedPreferences("user_details",MODE_PRIVATE);

        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Even geduld aub...");
        mProgress.setMessage("Even geduld aub...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.login_button:
            {
                Log.i("BUTTON:", "Login Activity > Login.");
                attemptLogin();
            }
            break;
            case R.id.recovery_button:
            {
                Log.i("BUTTON:", "Login Activity > Password Recovery.");
                Intent messageRegistration = new Intent(this, PasswordRecovery.class);
                startActivity(messageRegistration);
            }
            break;
            case R.id.register_button:
            {
                Log.i("BUTTON:", "Login Activity > Register.");
                Intent messageRegistration = new Intent(this, Registration.class);
                startActivity(messageRegistration);
            }
            default:
                break;
        }
    }

    public void attemptLogin()
    {
        if (mAuthTask != null)
        {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        View focusView;

        // Check for a valid email address.
        successfulValidationEmail = inputValidator.validateEmail(mEmailView);

        if (!successfulValidationEmail)
        {
            Log.d("TEST", "!successfulValidationEmail");
            focusView = mEmailView;
            focusView.requestFocus();
            return;
        }

        // Check for a valid password, if the user entered one.
        successfulValidationPassword = inputValidator.validatePassword(mPasswordView);
        if (!successfulValidationPassword)
        {
            focusView = mPasswordView;
            focusView.requestFocus();
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);
    }

    private void showProgress(final boolean show)
    {
        if (show)
        {
            mProgress.show();
        }
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {
        private String mEmail, mPassword;
        private DummyDatabase dummyDB;
        private UserDAO userDAO;
        private User user;

        private UserLoginTask(String email, String password)
        {
            mEmail = email;
            mPassword = password;
            dummyDB = DummyDatabase.getDatabase(getApplication());
            userDAO = dummyDB.userDAO();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                user = userDAO.getByEmail(mEmail);
                if (user != null)
                {
                    if (user.getM_password().equals(mPassword))
                    {
                        return true;
                    }
                }
                else
                {
                    throw new InterruptedException();
                }
            }
            catch (InterruptedException e)
            {
                Log.e("UserLoginTask", e.toString());
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthTask = null;
            showProgress(false);

            if (success)
            {
                finish();

                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("user_id", user.getId());
                editor.putString("first_name", user.getM_first_name());
                editor.putString("last_name", user.getM_last_name());
                editor.putString("email", user.getM_email());
                editor.apply();

                showProgress(false);
                Intent mainHomeScreenBinder = new Intent(Login.this, Home.class);
                startActivity(mainHomeScreenBinder);
            }
            else
            {
                mPasswordView.requestFocus();
                mProgress.dismiss();
                Toast.makeText(Login.this, "De ingevoerde gegevens kloppen niet", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }

        private void showProgress(final boolean show)
        {
            if (show)
            {
                mProgress.show();
            }
        }
    }
}