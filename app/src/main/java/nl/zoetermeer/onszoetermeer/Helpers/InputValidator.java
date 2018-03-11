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

    public void validateNotNull(EditText editText) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            editText.setFocusable(true);
            editText.setError("Veld mag niet leeg zijn!");
            editText.requestFocus();
        }
    }

    public void validatePassword(EditText editText1, EditText editText2) {
        String input1 = editText1.getText().toString();
        String input2 = editText2.getText().toString();

        if (input1.length() == 0) {
            editText1.setFocusable(true);
            editText1.setError("Veld mag niet leeg zijn!");
            editText1.requestFocus();
        } else if (input2.length() == 0) {
            editText2.setFocusable(true);
            editText2.setError("Veld mag niet leeg zijn!");
            editText2.requestFocus();
        } else if (!input1.equals(input2)) {
            editText1.setFocusable(true);
            editText1.setError("Wachtwoorden niet gelijk!");
            editText1.requestFocus();
            editText2.setFocusable(true);
            editText2.setError("Wachtwoorden niet gelijk!");
            editText2.requestFocus();
        }
    }

}