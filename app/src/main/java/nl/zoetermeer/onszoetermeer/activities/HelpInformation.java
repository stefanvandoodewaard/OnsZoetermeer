package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class HelpInformation extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_information);
        Log.i("ACTIVITY:", "HelpInformation created.");

        useToolbar();
    }

    public void sendMessage(View view) {
        String number;
        switch (view.getId()) {
            case R.id.call_112: {
                Log.i("BUTTON:", "Help info > 112.");

                number = "112";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);
            }
            break;
            case R.id.call_14079: {
                Log.i("BUTTON:", "Help info > 14079.");

                number = "14079";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);
            }
            break;
            case R.id.help_info_back: {
                Log.i("BUTTON:", "Help info > back.");
                finish();
            }
        }
    }
}
