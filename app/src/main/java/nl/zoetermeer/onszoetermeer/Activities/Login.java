package nl.zoetermeer.onszoetermeer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nl.zoetermeer.onszoetermeer.Data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.Helpers.InputValidator;
import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.R;


public class Login extends AppCompatActivity
{
    private DummyDatabase dummyDB;
    private InputValidator inputValidator;
    private boolean validationStatus;
    EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Log.i("ACTIVITY:", "Login created.");

        dummyDB = DummyDatabase.getDatabase(getApplicationContext());
        inputValidator = new InputValidator();

        validationStatus = false;

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_email);


        addListeners();

    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.login_button:
            {
                Log.i("BUTTON:", "Login Activity > Login.");
                Intent mainHomeScreenBinder = new Intent(this, Home.class);
                startActivity(mainHomeScreenBinder);
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
            break;
            default:
            {
                setContentView(R.layout.activity_login_screen);
            }
        }
    }

    public void addListeners() {

        loginEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputValidator.validateEmail(loginEmail);
            }
        });

    }
}