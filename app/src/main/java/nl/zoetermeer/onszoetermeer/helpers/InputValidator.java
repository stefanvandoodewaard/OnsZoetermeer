package nl.zoetermeer.onszoetermeer.helpers;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.util.Patterns;

import java.util.List;

import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.models.User;

public class InputValidator
{

    public void validateEmail(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setError("Veld mag niet leeg zijn!");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            editText.setError("Geen geldig email adres!");
        }
    }

    public void validateName(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setError("Veld mag niet leeg zijn!");

        } else if (!input.matches("[a-zA-Z ]+")) {
            editText.setError("Cijfers&tekens niet toegestaan!");
        }
    }

    public void validateNotNull(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setError("Veld mag niet leeg zijn!");
        }
    }


    public void validatePassword(EditText editText1, EditText editText2) {
        String input1 = editText1.getText().toString();
        String input2 = editText2.getText().toString();

        if (!input1.equals(input2)) {
            editText1.setError("Wachtwoorden niet gelijk!");
            editText2.setError("Wachtwoorden niet gelijk!");
        }
    }



    private class selectEmailsAsync extends AsyncTask<Void,Integer,List<User>>
    {
        private UserDAO userDAO;
        private DummyDatabase dummyDB;
        private String email;

        selectEmailsAsync(String email, Application application) {
            dummyDB = DummyDatabase.getDatabase(application);
            userDAO = dummyDB.userDAO();
            this.email = email;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDAO.getByEmail(email);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);

            Log.d("ASYNC-SELECT: ",users.size()+" row(s) found.");




        }
    }

}