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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment loginFragment, registerFragment, forgotPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transaction = getSupportFragmentManager().beginTransaction();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        forgotPasswordFragment = new ForgotPasswordFragment();

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

    public void registerOnCreate(){
        findViewById(R.id.login_button).setOnClickListener(view -> {
            setFragment("loginFragment");
        });

        findViewById(R.id.signup_button).setOnClickListener(view -> {
            if (registerFormIsValid()) {

                String userDataJson = generateRegisterJson();
                UserResponse response = new NetworkUtilities(this).makeRequest(new SignUpRequest(userDataJson, this));

                Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                if (response.isAccess())
                    setFragment("registerFragment");
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

            ((EditText)findViewById(R.id.editText_email_login)).setText("david@gemail.com");
            ((EditText)findViewById(R.id.editText_password)).setText("12345");
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
                    editor.commit();

                    Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else
                    //Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

        // EditText password = ((EditText)findViewById(R.id.editText_password));
        // TODO: Confirm password?
        // EditText confirmPassword = ((EditText)findViewById(R.id.editText_password));

        if (!editTextIsValid(findViewById(R.id.editText_firstName), 1, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_lastNames), 1, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_username), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_email), 5, true)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.editText_password_register), 5, false)) isValid = false;
        //if (!editTextIsValid(findViewById(R.id.confirmPasswordTextViewRegister), 5, false)) isValid = false;

        /*if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            password.setError(getString(R.string.passwords_do_not_match));
            confirmPassword.setError(getString(R.string.passwords_do_not_match));
            isValid = false;
        }*/

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

        String encryptedPass = encryptText(((EditText) findViewById(R.id.editText_password_register))
                                .getText().toString());

        return  "{" +
                "\"name\": \"" + ((EditText) findViewById(R.id.editText_firstName)).getText().toString() + "\"," +
                "\"surnames\": \"" + ((EditText) findViewById(R.id.editText_lastNames)).getText().toString() + "\"," +
                "\"userName\": \"" + ((EditText) findViewById(R.id.editText_username)).getText().toString() + "\"," +
                "\"email\": \"" + ((EditText) findViewById(R.id.editText_email)).getText().toString() + "\"," +
                "\"password\": \"" + encryptedPass + "\"" +
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

    /*private void changePassword_onCreate() {
        findViewById(R.id.userFormBack).setOnClickListener(v -> {
            setFragmentLayout("sign_in");
        });

        (findViewById(R.id.resetButton)).setOnClickListener(v -> {
            if (changePasswordFormIsValid()){
                RequestResponse response = new NetworkUtilites(this).makeRequest(new ChangePasswordRequest(generateChangePasswordJson(), this));
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.isAccess()) {
                    setFragmentLayout("sign_in");
                }
            }
        });
    }*/

    /*private boolean changePasswordFormIsValid() {
        boolean isValid = true;

        EditText password = ((EditText)findViewById(R.id.newPasswordTextViewReset));
        EditText confirmPassword = ((EditText)findViewById(R.id.confirmNewPasswordTextViewReset));

        if (!editTextIsValid(findViewById(R.id.usernameEditTextChangePass), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.oldPasswordTextViewReset), 5, false)) isValid = false;
        if (!editTextIsValid(password, 5, false)) isValid = false;
        if (!editTextIsValid(confirmPassword, 5, false)) isValid = false;

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            password.setError(getString(R.string.passwords_do_not_match));
            confirmPassword.setError(getString(R.string.passwords_do_not_match));
            isValid = false;
        }

        return isValid;
    }*/

    /*private String generateChangePasswordJson() {
        return  "{" +
                "\"username\": \"" + ((EditText) findViewById(R.id.usernameEditTextChangePass)).getText().toString() + "\"," +
                "\"oldPassword\": \"" + ((EditText) findViewById(R.id.oldPasswordTextViewReset)).getText().toString() + "\"," +
                "\"newPassword\": \"" + ((EditText) findViewById(R.id.newPasswordTextViewReset)).getText().toString() + "\"" +
                "}";
    }*/
}