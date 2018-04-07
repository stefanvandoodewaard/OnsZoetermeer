package nl.zoetermeer.onszoetermeer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.RequestDAO;
import nl.zoetermeer.onszoetermeer.models.Request;
import nl.zoetermeer.onszoetermeer.models.Request.RequestType;

public class RequestDetails extends Base
{
    private int userId, requestTypeId;
    private RequestType requestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        userId = pref.getInt("user_id", 0);

        Bundle bundleDetails = getIntent().getExtras();
        if (bundleDetails != null) {
            TextView request_detail_type = findViewById(R.id.request_details_name);
            requestTypeId = bundleDetails.getInt("request_type");
            Log.i("ACTIVITY: ", "RequestDetails succesfully created, requestTypeId = " + requestTypeId);

            if (requestTypeId == 0) {
                request_detail_type.setText("Maaltijd");
                requestType = RequestType.Maaltijd;
            } else if (requestTypeId == 1) {
                request_detail_type.setText("Boodschappen");
                requestType = RequestType.Boodschappen;
            } else if (requestTypeId == 2) {
                request_detail_type.setText("Klusjes");
                requestType = RequestType.Klusjes;
            } else if (requestTypeId == 3) {
                request_detail_type.setText("Vervoer");
                requestType = RequestType.Vervoer;
            }
        }
    }

    @Override
    public boolean useToolbar()
    {
        return false;
    }

    public void onClick(View view) {
        EditText request_input = findViewById(R.id.request_input);
        Button request_button = findViewById(R.id.request_send_button);
        String input = request_input.getText().toString();
        if (!input.isEmpty()) {
            Request request = new Request(userId, input, new Date(), requestType);
            new insertRequestAsync().execute(request);
            Context context = getApplicationContext();
            Drawable drawableInputValid = context.getResources().getDrawable(R.drawable.ic_input_valid);
            request_button.setVisibility(View.INVISIBLE);
            request_input.setText("");
            request_input.setHint("");
            request_input.setBackground(drawableInputValid);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    finish();
                }
            }, 1500);
        }
    }

    private class insertRequestAsync extends AsyncTask<Request, Void, Void>
    {
        private DummyDatabase dummyDB;
        private RequestDAO requestDAO;

        insertRequestAsync() {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            requestDAO = dummyDB.requestDAO();
        }

        @Override
        protected Void doInBackground(final Request... params) {
            requestDAO.insert(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("insertRequestAsync", "Request row inserted.");
            Toast.makeText(RequestDetails.this, "Hulpverzoek geregistreerd", Toast.LENGTH_LONG).show();
        }
    }
}
