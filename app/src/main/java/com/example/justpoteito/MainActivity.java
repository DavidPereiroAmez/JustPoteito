package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.justpoteito.fragments.ForgotPasswordFragment;
import com.example.justpoteito.fragments.LoginFragment;
import com.example.justpoteito.fragments.RegisterFragment;

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

}