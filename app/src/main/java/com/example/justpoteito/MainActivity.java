package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.justpoteito.fragments.ForgotPasswordFragment;
import com.example.justpoteito.fragments.LoginFragment;
import com.example.justpoteito.fragments.RegisterFragment;
import com.example.justpoteito.fragments.ResetPasswordFragment;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment loginFragment, registerFragment, forgotPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        forgotPasswordFragment = new ForgotPasswordFragment();


        setFragment("loginFragment");

    }

    public void setFragment(String fragment) {
        switch (fragment) {
            case "registerFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).runOnCommit(new Runnable(){

                    @Override
                    public void run() {
                        registerOnCreate();
                    }
                }).commit();
                break;

            case "loginFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,loginFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { loginOnCreate(); }
                }).commit();
                break;

            case "forgotPasswordFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,forgotPasswordFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { forgotPasswordOnCreate(); }
                }).commit();
                break;

            default:
                //nothing de nothing de momento
        }
    }

    public void registerOnCreate(){
        findViewById(R.id.login_button).setOnClickListener(view -> {
            setFragment("loginFragment");
        });
        findViewById(R.id.signup_button).setOnClickListener(view -> {
            System.out.println("Nada de momento");
        });
    }

    public void forgotPasswordOnCreate(){
        findViewById(R.id.forgotPassword_send_button).setOnClickListener(view -> {
            System.out.println("Nada de momento");
        });
        findViewById(R.id.login_forgot_password).setOnClickListener(view -> {
            setFragment("loginFragment");
        });
    }

    public void loginOnCreate() {

        findViewById(R.id.signup_button).setOnClickListener(view -> {
            setFragment("registerFragment");
        });
        findViewById(R.id.text_forgot_password).setOnClickListener(view -> {
            System.out.println("probando");
            setFragment("forgotPasswordFragment");
        });
        findViewById(R.id.login_button).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ExplorerActivity.class);
            //intent.putExtra("fragment", "register");
            //setContentView(R.layout.recipe);
            startActivity(intent);
        });
    }

    private boolean registerFormIsValid() {
        boolean isValid = true;

        if (!editTextIsValid(findViewById(R.id.editText_firstName), 1, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_lastNames), 1, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_username), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_email), 5, true)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_password_register), 5, false)) isValid = false;

        return isValid;
    }

    private boolean loginFormIsValid() {
        boolean isValid = true;

        if (!editTextIsValid(findViewById(R.id.editText_email_login), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_password), 5, false)) isValid = false;

        return isValid;
    }

    public boolean editTextIsValid(EditText editText, int minimumLength, boolean isEmail) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0 && !isEmail) {
            editText.setError(getString(R.string.empty_form_field));
            return false;
        }

        if (text.length() > 0 && text.length() < minimumLength && !isEmail) {
            editText.setError(getString(R.string.short_form_filed) + " " + minimumLength + " " + getString(R.string.character));
            return false;
        }

        if (isEmail && !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            editText.setError(getString(R.string.email_required_form_field));
            return false;
        }

        return true;
    }

    private String generateRegisterJson() {
        return  "{" +
                "\"name\": \"" + ((EditText) findViewById(R.id.editText_firstName)).getText().toString() + "\"," +
                "\"surnames\": \"" + ((EditText) findViewById(R.id.editText_lastNames)).getText().toString() + "\"," +
                "\"userName\": \"" + ((EditText) findViewById(R.id.editText_username)).getText().toString() + "\"," +
                "\"email\": \"" + ((EditText) findViewById(R.id.editText_email)).getText().toString() + "\"," +
                "\"password\": \"" + ((EditText) findViewById(R.id.editText_password_register)).getText().toString() + "\"" +
                "}";
    }

    private String generateLoginJson() {
        return  "{" +
                "\"email\": \"" + ((EditText) findViewById(R.id.editText_email_login)).getText().toString() + "\"," +
                "\"password\": \"" + ((EditText) findViewById(R.id.editText_password)).getText().toString() + "\"" +
                "}";

        /*return  "{" +
                "\"email\": \"" + "david@gemail.com" + "\"," +
                "\"password\": \"" + "12345" + "\"" +
                "}";*/
    }

}