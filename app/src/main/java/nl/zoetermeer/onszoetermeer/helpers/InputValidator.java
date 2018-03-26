package nl.zoetermeer.onszoetermeer.helpers;

import android.widget.EditText;
import android.util.Patterns;

public class InputValidator
{

    public boolean validateEmail(EditText emailView)
    {
        String input = emailView.getText().toString();

        if (input.length() == 0)
        {
            emailView.setError("Veld mag niet leeg zijn!");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches())
        {
            emailView.setError("Geen geldig email adres!");
            return false;
        }

        return true;
    }

    public boolean validatePassword(EditText passwordView)
    {
        String input = passwordView.getText().toString();

        if (input.length() == 0)
        {
            passwordView.setError("Veld mag niet leeg zijn!");
            return false;
        }

        return true;
    }

    public void validateName(EditText editText)
    {
        String input = editText.getText().toString();

        if (input.length() == 0)
        {
            editText.setError("Veld mag niet leeg zijn!");
        }
        else if (!input.matches("[a-zA-Z ]+"))
        {
            editText.setError("Cijfers&tekens niet toegestaan!");
        }
    }

    public void validateNotNull(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setError("Veld mag niet leeg zijn!");
        }
    }

//    public void validateNotNullPassword(EditText editText) {
//        String input = editText.getText().toString();
//
//        if (input.length() == 0) {
//            EditText.setError("Veld mag niet leeg zijn!");
//        }
//    }

}