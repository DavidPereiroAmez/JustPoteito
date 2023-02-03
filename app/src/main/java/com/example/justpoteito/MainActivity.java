package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.justpoteito.database.DatabaseHelper;
import com.example.justpoteito.fragments.ForgotPasswordFragment;
import com.example.justpoteito.fragments.LoginFragment;
import com.example.justpoteito.fragments.RegisterFragment;
import com.example.justpoteito.models.User;
import com.example.justpoteito.models.UserResponse;
//import com.example.justpoteito.network.CreateUserRequest;
import com.example.justpoteito.network.ErrorShow;
import com.example.justpoteito.network.request.SendEmailRequest;
import com.example.justpoteito.network.request.SignUpRequest;
import com.example.justpoteito.network.request.LoginRequest;
import com.example.justpoteito.network.NetworkUtilities;
import com.example.justpoteito.security.RsaEncrypter;
import com.example.justpoteito.security.RsaFileReader;
import com.example.justpoteito.utilities.FormValidator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.Normalizer;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment loginFragment, registerFragment, forgotPasswordFragment;
    FormValidator formValidator;
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transaction = getSupportFragmentManager().beginTransaction();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        forgotPasswordFragment = new ForgotPasswordFragment();
        formValidator = new FormValidator(this);

        setFragment("loginFragment");
    }

    public void setFragment(String fragment) {
        switch (fragment) {
            case "registerFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).runOnCommit(() -> registerOnCreate()).commit();
                transaction.addToBackStack(null);
                break;

            case "loginFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,loginFragment).runOnCommit(() -> loginOnCreate()).commit();
                transaction.addToBackStack(null);
                break;

            case "forgotPasswordFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,forgotPasswordFragment).runOnCommit(() -> forgotPasswordOnCreate()).commit();
                transaction.addToBackStack(null);
                break;

            default:
                showErrorInForm();
                break;
        }
    }

    public void showErrorInForm() {
        new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
    }

    public void registerOnCreate() {
        findViewById(R.id.login_button).setOnClickListener(view -> {
            setFragment("loginFragment");
        });

        findViewById(R.id.signup_button).setOnClickListener(view -> {


            if (registerFormIsValid()) {

                String userDataJson = generateRegisterJson();
                UserResponse response = new NetworkUtilities(this).makeRequest(new SignUpRequest(userDataJson, this));

                Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                if (response.isAccess()){
                    setFragment("loginFragment");
                }

            }else{
                Toast.makeText(this, ErrorShow.userNotValid(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginOnCreate() {
        findViewById(R.id.signup_button).setOnClickListener(view -> {
            setFragment("registerFragment");
        });

        findViewById(R.id.text_forgot_password).setOnClickListener(view -> {
            setFragment("forgotPasswordFragment");
        });

        CheckBox checkRemember = findViewById(R.id.remember_checkBox);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        if (!databaseHelper.isEmpty()) {
            User user = databaseHelper.getAllUsers();
            ((EditText)findViewById(R.id.editText_email_login)).setText(user.getEmail());
            ((EditText)findViewById(R.id.editText_password)).setText(user.getPassword());
        }

        findViewById(R.id.login_button).setOnClickListener(view -> {
            Intent intent = new Intent(this, ExplorerActivity.class);

            String email = ((EditText)findViewById(R.id.editText_email_login)).getText().toString();
            String password = ((EditText)findViewById(R.id.editText_password)).getText().toString();

            if (checkRemember.isChecked()) {
                if ((!databaseHelper.isEmpty() && databaseHelper.deleteUser() == 1) || databaseHelper.isEmpty()) {
                    if (databaseHelper.createUser(email, password)) {
                    }
                }
            }

            if (loginFormIsValid()) {

                UserResponse loginResponse = new NetworkUtilities(this).makeRequest(new LoginRequest(generateLoginJson(), this));

                if (loginResponse.isAccess()) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("user_id", loginResponse.getId());
                    editor.putString("username", loginResponse.getUsername());
                    editor.putString("user_realName", loginResponse.getName());
                    editor.putString("surnames", loginResponse.getSurnames());
                    editor.putString("email", loginResponse.getEmail());

                    editor.commit();

                    Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(this, ErrorShow.connectionError(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void forgotPasswordOnCreate(){
        findViewById(R.id.forgotPassword_send_button).setOnClickListener(view -> {
            String email = ((EditText)findViewById(R.id.editText_type_your_email)).getText().toString();

            String response = new NetworkUtilities(this)
                    .makeRequest(new SendEmailRequest(email, this));

            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.login_forgot_password).setOnClickListener(view -> {
            setFragment("loginFragment");
        });
    }

    private boolean registerFormIsValid() {
        boolean isValid = true;

        if (!formValidator.editTextIsValid(findViewById(R.id.editText_firstName), 1, false)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_lastNames), 1, false)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_username), 5, false)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_email), 5, true)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_password_register), 5, false)) isValid = false;

        return isValid;
    }

    private boolean loginFormIsValid() {
        boolean isValid = true;

        if (!formValidator.editTextIsValid(findViewById(R.id.editText_email_login), 5, false)) isValid = false;
        if (!formValidator.editTextIsValid(findViewById(R.id.editText_password), 5, false)) isValid = false;

        return isValid;
    }

    private String generateRegisterJson() {

        String encryptedPass = encryptText(((EditText) findViewById(R.id.editText_password_register))
                                .getText().toString());

        return  "{" +
                "\"name\": \"" + ((EditText) findViewById(R.id.editText_firstName)).getText().toString() + "\"," +
                "\"surnames\": \"" + ((EditText) findViewById(R.id.editText_lastNames)).getText().toString() + "\"," +
                "\"userName\": \"" + ((EditText) findViewById(R.id.editText_username)).getText().toString() + "\"," +
                "\"email\": \"" + ((EditText) findViewById(R.id.editText_email)).getText().toString() + "\"," +
                "\"password\": \"" + encryptedPass  + "\"" +
                "}";
    }

    private String generateLoginJson() {

        String encryptedPass = encryptText(((EditText) findViewById(R.id.editText_password))
                            .getText().toString());

        return  "{" +
                "\"email\": \"" + ((EditText) findViewById(R.id.editText_email_login)).getText().toString() + "\"," +
                "\"password\": \"" + encryptedPass + "\"" +
                "}";
    }

    private String encryptText(String password) {
        byte[] key = RsaFileReader.readRsaFile("public.key", MainActivity.this);
        return RsaEncrypter.encryptText(password, key);
    }
}