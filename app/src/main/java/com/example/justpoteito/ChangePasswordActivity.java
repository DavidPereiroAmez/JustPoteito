package com.example.justpoteito;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justpoteito.models.RequestResponse;
import com.example.justpoteito.network.ChangePasswordRequest;
import com.example.justpoteito.network.NetworkUtilities;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassword, newPassword, confirmPassword;
    Button changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        oldPassword = findViewById(R.id.editText_oldPassword);
        newPassword = findViewById(R.id.editText_newPassword);
        confirmPassword = findViewById(R.id.editText_confirmPassword);

        changePass = findViewById(R.id.resetPassword_button);
        (findViewById(R.id.resetPassword_button)).setOnClickListener(v -> {
            if (changePasswordFormIsValid()) {
                Intent intent = getIntent();
                String email = (intent != null) ? intent.getStringExtra("email") : "";
                RequestResponse response = new NetworkUtilities(this).makeRequest(new ChangePasswordRequest(generateChangePasswordJson(email), this));
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.isAccess()) {
                    setContentView(R.layout.activity_profile);
                }
            }
        });
    }

    private String generateChangePasswordJson(String email) {
        return  "{" +
                "\"email\": \"" + email + "\"," +
                "\"oldPassword\": \"" + ((EditText) findViewById(R.id.editText_oldPassword)).getText().toString() + "\"," +
                "\"newPassword\": \"" + ((EditText) findViewById(R.id.editText_newPassword)).getText().toString() + "\"" +
                "}";
    }

    public boolean editTextIsValid(EditText editText, int minimumLength) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(getString(R.string.empty_form_field));
            return false;
        }

        if (text.length() > 0 && text.length() < minimumLength) {
            editText.setError(getString(R.string.short_form_filed) + " " + minimumLength + " " + getString(R.string.character));
            return false;
        }

        return true;
    }

    private boolean changePasswordFormIsValid() {
        boolean isValid = true;

        EditText newPassword = ((EditText)findViewById(R.id.editText_newPassword));
        EditText confirmPassword = ((EditText)findViewById(R.id.editText_confirmPassword));

        //if (!editTextIsValid(findViewById(R.id.usernameEditTextChangePass), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_oldPassword), 5)) isValid = false;
        if (!editTextIsValid(newPassword, 5)) isValid = false;
        if (!editTextIsValid(confirmPassword, 5)) isValid = false;

        if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            newPassword.setError(getString(R.string.passwords_do_not_match));
            confirmPassword.setError(getString(R.string.passwords_do_not_match));
            isValid = false;
        }

        return isValid;

    }
}
