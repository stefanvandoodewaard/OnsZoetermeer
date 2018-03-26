package nl.zoetermeer.onszoetermeer.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.helpers.InputValidator;


public class Login extends AppCompatActivity
{
    private InputValidator inputValidator;
    private EditText mEmailView, mPasswordView;
    private ProgressDialog mProgress;
    private View mLoginScreenView;

    boolean successfulValidationPassword = false;
    boolean successfulValidationEmail = false;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "doodewaard@hotmail.com:1234"
    };

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Log.i("ACTIVITY:", "Login created.");

        inputValidator = new InputValidator();

        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);
        mLoginScreenView = findViewById(R.id.login_screen);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.login_button:
            {
                attemptLogin();

                if (successfulValidationPassword && successfulValidationEmail)
                {
                    Log.i("BUTTON:", "Login Activity > Login.");
                    Intent mainHomeScreenBinder = new Intent(this, Home.class);
                    startActivity(mainHomeScreenBinder);
                }
            }
            break;
            case R.id.recovery_button:
            {
                Log.i("BUTTON:", "Login Activity > Password Recovery.");
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


    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show)
    {
        if(show)
        {
            mProgress.show();
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password)
        {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.
//            successfulVerificationEmail = inputValidator.verifyExistingEmail(mEmailView, getApplication());
            try
            {
                // Simulate network access.
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS)
            {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail))
                {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthTask = null;
            showProgress(false);

            if (success)
            {
                finish();
            }
            else
            {
                inputValidator.validatePassword(mPasswordView);
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }
    }
}