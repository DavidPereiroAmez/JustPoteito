package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.justpoteito.models.User;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    User user;

    //TextView id;
    TextView profile, name, surname, username, email, deleteUser;
    Button btn_changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        });

        //id = findViewById(R.id.userIdTextView);
        profile = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        deleteUser = findViewById(R.id.deleteUser);

        btn_changePass = findViewById(R.id.btn_change_pass);

    }

    @Override
    public void onClick(View view) {

        //change password button
    }
}