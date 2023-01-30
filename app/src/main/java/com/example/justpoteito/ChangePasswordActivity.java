package com.example.justpoteito;

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
                RequestResponse response = new NetworkUtilities(this).makeRequest(new ChangePasswordRequest(generateChangePasswordJson(), this));
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.isAccess()) {
                    setContentView(R.layout.activity_profile);
                }
            }
        });
    }

    private String generateChangePasswordJson() {
        return  "{" +
                //"\"username\": \"" + ((EditText) findViewById(R.id.usernameEditTextChangePass)).getText().toString() + "\"," +
                "\"oldPassword\": \"" + ((EditText) findViewById(R.id.editText_oldPassword)).getText().toString() + "\"," +
                "\"newPassword\": \"" + ((EditText) findViewById(R.id.editText_newPassword)).getText().toString() + "\"" +
                "}";
    }

    private boolean changePasswordFormIsValid() {
        boolean isValid = true;

        EditText newPassword = ((EditText)findViewById(R.id.editText_newPassword));
        EditText confirmPassword = ((EditText)findViewById(R.id.editText_confirmPassword));

        //if (!editTextIsValid(findViewById(R.id.usernameEditTextChangePass), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_oldPassword), 5, false)) isValid = false;
        if (!editTextIsValid(newPassword, 5, false)) isValid = false;
        if (!editTextIsValid(confirmPassword, 5, false)) isValid = false;

        if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            newPassword.setError(getString(R.string.passwords_do_not_match));
            confirmPassword.setError(getString(R.string.passwords_do_not_match));
            isValid = false;
        }

        return isValid;

    }
}