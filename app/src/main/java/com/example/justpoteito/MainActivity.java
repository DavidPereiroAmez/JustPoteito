package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.justpoteito.network.LoginRequest;

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
            if (registerFormIsValid()) {

                String userDataJson = generateRegisterJson();
                UserResponse response = new NetworkUtilites(this).makeRequest(new CreateUserRequest(userDataJson, this));


                Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                if (response.isAccess())
                    setFragmentLayout("sign_in");

            }
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

        CheckBox checkRemember = findViewById(R.id.remember_checkBox);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        if (!databaseHelper.isEmpty()) {
            User user = databaseHelper.getAllUsers();
            ((EditText)findViewById(R.id.editText_username)).setText(user.getUsername());
            ((EditText)findViewById(R.id.editText_password)).setText(user.getPassword());
        }

        findViewById(R.id.login_button).setOnClickListener(view -> {
            Intent intent = new Intent(this, ExplorerActivity.class);

            String username = ((EditText)findViewById(R.id.editText_username)).getText().toString();
            String password = ((EditText)findViewById(R.id.editText_password)).getText().toString();

            if (checkRemember.isChecked()) {
                if ((!databaseHelper.isEmpty() && databaseHelper.deleteUser() == 1) || databaseHelper.isEmpty()) {
                    if (databaseHelper.createUser(username, password)) {
                        //Toast.makeText(this, username + " created.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            if (signInFormIsValid()) {

                UserResponse loginResponse = new NetworkUtilites(this).makeRequest(new LoginRequest(generateLogInJson(), this));

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
                    Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.login_button).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ExplorerActivity.class);
            //intent.putExtra("fragment", "register");
            //setContentView(R.layout.recipe);
            startActivity(intent);
        });
    }

    public void showErrorInForm() {
        toolbarTitle.setText(getString(R.string.error));
        new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
    }

    private boolean signInFormIsValid() {
        boolean isValid = true;

        if (!editTextIsValid(findViewById(R.id.usernameTextViewSignIn), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.passwordTextViewSignIn), 5, false)) isValid = false;

        return isValid;

    }

    private boolean registerFormIsValid() {
        boolean isValid = true;

        EditText password = ((EditText)findViewById(R.id.passwordTextViewRegister));
        EditText confirmPassword = ((EditText)findViewById(R.id.confirmPasswordTextViewRegister));

        if (!editTextIsValid(findViewById(R.id.usernameTextViewRegister), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.firstNameTextViewRegister), 1, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.emailTextViewRegister), 5, true)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.confirmPasswordTextViewRegister), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.passwordTextViewRegister), 5, false)) isValid = false;
        if (!editTextIsValid(findViewById(R.id.lastNamesTextViewRegister), 1, false)) isValid = false;

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            password.setError(getString(R.string.passwords_do_not_match));
            confirmPassword.setError(getString(R.string.passwords_do_not_match));
            isValid = false;
        }

        return isValid;
    }

    private boolean changePasswordFormIsValid() {
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
                "\"username\": \"" + ((EditText) findViewById(R.id.usernameTextViewRegister)).getText().toString() + "\"," +
                "\"firstname\": \"" + ((EditText) findViewById(R.id.firstNameTextViewRegister)).getText().toString() + "\"," +
                "\"lastnames\": \"" + ((EditText) findViewById(R.id.lastNamesTextViewRegister)).getText().toString() + "\"," +
                "\"email\": \"" + ((EditText) findViewById(R.id.emailTextViewRegister)).getText().toString() + "\"," +
                "\"password\": \"" + ((EditText) findViewById(R.id.passwordTextViewRegister)).getText().toString() + "\"" +
                "}";
    }

    private String generateLogInJson() {
        return  "{" +
                "\"username\": \"" + ((EditText) findViewById(R.id.editText_username)).getText().toString() + "\"," +
                "\"password\": \"" + ((EditText) findViewById(R.id.editText_password)).getText().toString() + "\"" +
                "}";
    }

    private String generateChangePasswordJson() {
        return  "{" +
                "\"username\": \"" + ((EditText) findViewById(R.id.usernameEditTextChangePass)).getText().toString() + "\"," +
                "\"oldPassword\": \"" + ((EditText) findViewById(R.id.oldPasswordTextViewReset)).getText().toString() + "\"," +
                "\"newPassword\": \"" + ((EditText) findViewById(R.id.newPasswordTextViewReset)).getText().toString() + "\"" +
                "}";
    }
}