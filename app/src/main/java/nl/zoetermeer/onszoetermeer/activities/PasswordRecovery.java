package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class PasswordRecovery extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.recovery_button:
            {
                Log.i("BUTTON:", " Password recovery > Send new password.");
            }
            break;
            case R.id.recovery_return_button:
            {
                Log.i("BUTTON:", " Password recovery > Return to login");
                Intent intentLogin = new Intent(this, Login.class);
                startActivity(intentLogin);
            }
            break;
            default:
                break;
        }
    }
}
