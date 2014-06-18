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
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForgotPassword extends Activity {

    SharedPreferences preference;
    String mobile, question, answer, password,json,emailID;
    public final String myPreference = "Myprefer";
    UserDetails userDetails;
    EditText email;
    TextView mobileErrorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.fortgot_password);
        Button continueButton = (Button) findViewById(R.id.continueButton);
        email = (EditText) findViewById(R.id.emailID);
        mobileErrorMessage = (TextView) findViewById(R.id.emailErrorMessage);
        preference = getSharedPreferences(myPreference, MODE_PRIVATE);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailID=email.getText().toString();
                Gson gson = new Gson();
                if (preference.contains(emailID)) {
                    Log.d("Test", "inside checking email");
                    json = preference.getString(emailID, "");
                    userDetails = gson.fromJson(json, UserDetails.class);
                    Intent intent = new Intent(ForgotPassword.this, CheckSecurityQuestion.class);
                    intent.putExtra("mobile", userDetails.getPhoneNumber());
                    intent.putExtra("question", userDetails.getQuestion());
                    intent.putExtra("answer", userDetails.getAnswer());
                    intent.putExtra("password",userDetails.getPassword());
                    startActivity(intent);
                }else{
                    mobileErrorMessage.setText("With this emailID No Account Exist");
                }
            }
        });
    }
}
