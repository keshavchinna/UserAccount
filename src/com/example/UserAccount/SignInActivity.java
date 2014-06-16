package com.example.UserAccount;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
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
    String emailId, userPassword;

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
                String email = userName.getText().toString();
                String pwd = password.getText().toString();
                if (preference.contains("emailKey")) {
                    emailId = preference.getString("emailKey", "");
                }
                if (preference.contains("passwordKey")) {
                    userPassword = preference.getString("passwordKey", "");
                }
                if (email.equals(emailId) && pwd.equals(userPassword)) {
                    Intent intent = new Intent(SignInActivity.this, Dashboard.class);
                    startActivity(intent);
                } else {
                    errorMessage.setText("Login failed check username/password");
                }
            }
        });


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
