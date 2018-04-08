package nl.zoetermeer.onszoetermeer.helpers;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Date;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.models.User;


public class VitalityTimeTrigger extends AsyncTask<Void, Void, Void>
{
    private Application application;
    private int userId;
    private UserDAO userDAO;
    private User user;
    private double totalDeducation;

    public VitalityTimeTrigger(Application application, int userId) {
        this.application = application;
        this.userId = userId;
        DummyDatabase dummyDB = DummyDatabase.getDatabase(application);
        userDAO = dummyDB.userDAO();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // get user & dates
        user = userDAO.getByID(userId);
        Date lastDate = user.getLoginDate();
        Date newDate = new Date();

        // if new user without last login date > skip time trigger
        if (lastDate != null) {
            Log.i("VitalityTimeTrigger", "Last login for " + user.getM_email() + " was at : " + lastDate);
            // get difference in milliseconds
            long diff = newDate.getTime() - lastDate.getTime();

            // various differences
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            // calulate percentages for the 20% per 5 hours requirement
            Float percentageMinutes = ((float) diffMinutes * 100 / 300);
            Float percentageHours = ((float) diffHours * 100) / 5;
            Float percentageDays = ((float) diffDays * 100) / ((5 * 100) / 24);
            double percentageTotal = percentageMinutes + percentageHours + percentageDays;
            totalDeducation = (percentageTotal / 100) * 20;

            // change users vitality percentages
            int mental = user.getM_vit_ment() - (int) totalDeducation;
            int physical = user.getM_vit_phys() -  (int) totalDeducation;
            user.setM_vit_ment(mental);
            user.setM_vit_phys(physical);
        }

        //set new login date
        user.setLoginDate(newDate);
        userDAO.update(user);
        Log.i("VitalityTimeTrigger", "New login for " + user.getM_email() + " is at : " + newDate);

        user = userDAO.getByID(userId);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.i("VitalityTimeTrigger", "User " + user.getM_email() + " gets a vitality deduction of " + totalDeducation + "%");
    }

}