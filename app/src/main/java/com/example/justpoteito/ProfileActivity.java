package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.justpoteito.fragments.ResetPasswordFragment;
import com.example.justpoteito.models.User;

public class ProfileActivity extends AppCompatActivity {

    User user;

    //TextView id;
    TextView profile, name, surname, username, email;
    Button btn_changePass, btn_deleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ExplorerActivity.class);
            startActivity(intent);
            finish();

        });

        findViewById(R.id.btn_change_pass).setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        });




        //id = findViewById(R.id.userIdTextView);
        profile = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);

        btn_deleteUser = findViewById(R.id.bnt_deleteUser);
        btn_changePass = findViewById(R.id.btn_change_pass);

    }
}