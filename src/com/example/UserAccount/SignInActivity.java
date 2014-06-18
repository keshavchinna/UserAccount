package com.example.UserAccount;

import android.app.Activity;
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


    public final String myPreference = "Myprefer";
    SharedPreferences preference;
    EditText userName, password;
    TextView errorMessage;
    Button signIn, signUp;
    String email, pwd, json;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.sign_in);

        userName = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);
        preference = getSharedPreferences(myPreference, MODE_PRIVATE);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To change body of implemented methods use File | Settings | File Templates.
                email = userName.getText().toString();
                pwd = password.getText().toString();
                Gson gson = new Gson();
                if (preference.contains(email)) {
                    Log.d("Test", "inside checking email");
                    json = preference.getString(email, "");
                    userDetails = gson.fromJson(json, UserDetails.class);
                    if (email.equals(userDetails.getEmail()) && pwd.equals(userDetails.getPassword())) {
                        Intent intent = new Intent(SignInActivity.this, Dashboard.class);
                        startActivity(intent);
                    } else {
                        errorMessage.setText("Login failed check username/password");
                    }
                } else {
                    Log.d("Test", "inside checking email else condition");
                    errorMessage.setText("Invalid Email Address,No Account Exist");
                }
            }


        });

    };

    public void signUp(View v) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void forgotPassword(View v) {
        Intent intent = new Intent(SignInActivity.this, ForgotPassword.class);
        startActivity(intent);
    }


}
