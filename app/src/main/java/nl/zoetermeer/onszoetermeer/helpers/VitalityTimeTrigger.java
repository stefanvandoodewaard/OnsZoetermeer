package nl.zoetermeer.onszoetermeer.helpers;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;


public class VitalityTimeTrigger extends AsyncTask<Void,Void, Void>
{
    private Application application;
    private int userId;
    private Date lastDate;
    private Date newDate;
    private UserDAO userDAO;

    public VitalityTimeTrigger(Application application, int userId, Date date) {
        this.application = application;
        this.userId = userId;
        this.lastDate = date;
        this.newDate = new Date();
        DummyDatabase dummyDB = DummyDatabase.getDatabase(application);
        userDAO = dummyDB.userDAO();
    }

        @Override
        protected Void doInBackground(Void... voids) {

            //HH converts hour in 24 hours format (0-23), day calculation
//            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            Date d1 = format.parse(lastDate.toString());
//            Date d2 = format.parse(newDate.toString());

            //in milliseconds
            long diff = lastDate.getTime() - newDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.i("VitalityTimeTrigger", diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.");

        return null;
    }

        @Override
        protected void onPostExecute(Void aVoid) {



    }

    }