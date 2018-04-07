package nl.zoetermeer.onszoetermeer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import nl.zoetermeer.onszoetermeer.R;

public class Help extends Base
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Log.i("ACTIVITY:", "Help created.");
    }

    public void sendMessage(View view) {
        Bundle bundle = new Bundle();
        Intent requestDetailsIntent = new Intent(this, RequestDetails.class);
        switch (view.getId()) {
            case R.id.help_meal_button: {
                Log.i("BUTTON:", "Help > Meal.");

                bundle.putInt("request_type", 0);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
            case R.id.help_shop_button: {
                Log.i("BUTTON:", "Help > Shop.");

                bundle.putInt("request_type", 1);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
            case R.id.help_chore_button: {
                Log.i("BUTTON:", "Help > Chore.");

                bundle.putInt("request_type", 2);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
            case R.id.help_transport_button: {
                Log.i("BUTTON:", "Help > Transport.");

                bundle.putInt("request_type", 3);
                requestDetailsIntent.putExtras(bundle);
                startActivity(requestDetailsIntent);
            }
            break;
        }
    }
}
