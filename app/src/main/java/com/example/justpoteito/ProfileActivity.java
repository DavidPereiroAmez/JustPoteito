package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.justpoteito.models.UserResponse;
import com.example.justpoteito.network.NetworkUtilities;
import com.example.justpoteito.network.request.DeleteUserRequest;
import com.example.justpoteito.network.request.LoginRequest;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.imageTrash).setOnClickListener(view -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
            int userId =  preferences.getInt("user_id", 0);

            String deleteResponse = new NetworkUtilities(this)
                    .makeRequest(new DeleteUserRequest(userId));

            new Toast(this).makeText(this, deleteResponse, Toast.LENGTH_LONG).show();

            if (deleteResponse.equals("User deleted")) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}