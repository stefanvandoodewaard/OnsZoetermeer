package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class Profile extends Base
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void sendMessage(View view)
    {
        switch (view.getId())
        {
            case R.id.chat_button:
            {
                Log.i("BUTTON:", "Profile > Chat.");
                Intent messageChat = new Intent(this, Chat.class);
                startActivity(messageChat);
            }
            break;
        }
    }
}
