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

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 16/6/14
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForgotPassword extends Activity {

    SharedPreferences preference;
    String mobile,question,answer,password;
    public final String myPreference = "Myprefer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.fortgot_password);
        Button continueButton=(Button)findViewById(R.id.continueButton);
        final EditText mobileNumber=(EditText)findViewById(R.id.mobileNumber);
        final TextView mobileErrorMessage=(TextView)findViewById(R.id.mobileErrorMessage);
        preference = getSharedPreferences(myPreference, MODE_PRIVATE);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(preference.contains("mobileKey")){
                 mobile=preference.getString("mobileKey","");
              }
                if(preference.contains("questionKey"))
                {
                  question=preference.getString("questionKey","");
                }
                if(preference.contains("questionKey"))
                {
                    question=preference.getString("questionKey","");
                }
                if(preference.contains("answerKey"))
                {
                    answer=preference.getString("answerKey","");
                }
                if(preference.contains("passwordKey"))
                {
                    password=preference.getString("passwordKey","");
                }
                Log.d("Test", "in ForgotPassword: "+answer);
                if(mobileNumber.getText().toString().equals(mobile)){
                   Intent intent=new Intent(ForgotPassword.this,CheckSecurityQuestion.class);
                   intent.putExtra("mobile",mobile);
                   intent.putExtra("question",question);
                    intent.putExtra("answer",answer);
                    intent.putExtra("password",password);
                   startActivity(intent);
                }
                else
                    mobileErrorMessage.setText("With this Mobile Number No Account Exist");
            }
        });
    }
}
