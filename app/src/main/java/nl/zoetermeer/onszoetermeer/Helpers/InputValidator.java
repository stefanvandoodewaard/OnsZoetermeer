package nl.zoetermeer.onszoetermeer.Helpers;


import android.widget.EditText;
import android.util.Patterns;

public class InputValidator
{

    public void validateEmail(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setFocusable(true);
            editText.setError("Veld mag niet leeg zijn!");
            editText.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            editText.setFocusable(true);
            editText.setError("Geen geldig email adres!");
            editText.requestFocus();
        }
    }

    public void validateName(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setFocusable(true);
            editText.setError("Veld mag niet leeg zijn!");
            editText.requestFocus();

        } else if (!input.matches("[a-zA-Z ]+")) {
            editText.setFocusable(true);
            editText.setError("Cijfers&tekens niet toegestaan!");
            editText.requestFocus();
        }
    }

}