package com.example.UserAccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * UserDetails: ehc
 * Date: 16/6/14
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SignInActivity extends Activity {

    static final String mylogin = "LoginDetails";
    private static final String name = "nameKey";
    private static final String pass = "passwordKey";
    private final String myPreference = "Myprefer";
    private SharedPreferences preference;
    private SharedPreferences loginDetailsPreferences;
    private EditText userName, password;
    private TextView errorMessage;
    private Button signIn, signUp;
    private String email, pwd, json;
    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.sign_in);
        getWidgets();
        checkingUserPreference();
        signIn.setOnClickListener(getListener());
    }

    private View.OnClickListener getListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = userName.getText().toString();
                pwd = password.getText().toString();
                validatingCredentials();
            }
        };
    }

    private void validatingCredentials() {
        Gson gson = new Gson();
        if (preference.contains(email)) {
            Log.d("Test", "inside checking email");
            json = preference.getString(email, "");
            userDetails = gson.fromJson(json, UserDetails.class);
            if (email.equals(userDetails.getEmail()) && pwd.equals(userDetails.getPassword())) {
                saveCredentials();
                startDashboard();
            } else {
                /*errorMessage.setText("Login failed check username/password");*/
                password.setError("Invalid Password");
            }
        } else {
            Log.d("Test", "inside checking email else condition");
           /* errorMessage.setText("Invalid Email Address,No Account Exist");*/
            userName.setError("Invalid Email Address,No Account Exist");

        }
    }

    private void startDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    private void saveCredentials() {
        SharedPreferences.Editor editor = loginDetailsPreferences.edit();
        editor.putString(name, email);
        editor.putString(pass, pwd);
        editor.commit();
    }

    private void getWidgets() {
        userName = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);
        preference = getSharedPreferences(myPreference, MODE_PRIVATE);
        loginDetailsPreferences = getSharedPreferences(mylogin, MODE_PRIVATE);
    }

    void checkingUserPreference() {
        loginDetailsPreferences = getSharedPreferences(mylogin, Context.MODE_PRIVATE);
        if (loginDetailsPreferences.contains(name)) {
            if (loginDetailsPreferences.contains(pass)) {
                startDashboard();
            }
        }
    }

    public void signUp(View v) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void forgotPassword(View v) {
        Intent intent = new Intent(SignInActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

}
