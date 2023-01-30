package com.example.justpoteito;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
