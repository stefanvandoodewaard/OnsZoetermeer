package nl.zoetermeer.onszoetermeer.Helpers;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.util.Patterns;

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

    public void validateNotNullPassword(EditText editText, TextInputLayout textInputLayout) {
        String input = editText.getText().toString();

        if (input.length() == 0) {
            textInputLayout.setError("Veld mag niet leeg zijn!");
        }
    }

    public void validatePassword(EditText editText1, EditText editText2) {
        String input1 = editText1.getText().toString();
        String input2 = editText2.getText().toString();

        if (input1.length() == 0) {
            editText1.setError("Veld mag niet leeg zijn!");
        } else if (!input1.equals(input2)) {
            editText1.setError("Wachtwoorden niet gelijk!");
        }
    }

}