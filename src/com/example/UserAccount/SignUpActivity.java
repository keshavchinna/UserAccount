package com.example.UserAccount;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 16/6/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SignUpActivity extends Activity {

    public final String myPreference = "Myprefer";
    SharedPreferences preference;
    EditText firstName, lastName, emailId, mobileNumber, password, confirmPassword, securityQuestion;
    Spinner questionAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.sign_up);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        emailId = (EditText) findViewById(R.id.email);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        securityQuestion = (EditText) findViewById(R.id.securityQuestion);
        questionAnswer=(Spinner)findViewById(R.id.chooseAnswer);
        preference = getSharedPreferences(myPreference, MODE_PRIVATE);
    }

    public void singnUp(View view) {
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String email = emailId.getText().toString();
        String mobile = mobileNumber.getText().toString();
        String pswrd = password.getText().toString();
        String confPassword = confirmPassword.getText().toString();
        String question = securityQuestion.getText().toString();
        String questionAns=questionAnswer.getSelectedItem().toString();
        Log.d("Test", "questionAnswer: " + questionAns);
        SharedPreferences.Editor editor = preference.edit();
        if (fName.equals("")) {
            firstName.setHint("First Name Required");
            firstName.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else

            editor.putString("firstNameKey", fName);
        if (lName.equals("")) {
            lastName.setHint("last Name Required");
            lastName.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else
            editor.putString("lastNameKey", lName);
        if (email.equals("")) {
            emailId.setHint("EmailId Required");
            emailId.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else
            editor.putString("emailKey", email);
        if (mobile.equals("")) {
            mobileNumber.setHint("Mobile Number Required");
            mobileNumber.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else
            editor.putString("mobileKey", mobile);
        if (pswrd.equals("")) {
            password.setHint("PassWord Required");
            password.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));

        } else if (confPassword.equals(pswrd)) {
                editor.putString("passwordKey", pswrd);
            } else {
                confirmPassword.setHint("Confirm Password Must match");
                confirmPassword.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        if (question.equals("")) {
            securityQuestion.setHint("Security Question Required");
            securityQuestion.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));

        } else
            editor.putString("questionKey", question);

        if (questionAns.equals("")) {
            /*questionAnswer.setPrompt("Security Question Answer Required");
            questionAnswer.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));*/
            Toast.makeText(getApplicationContext(),"Answer Required",Toast.LENGTH_LONG).show();
        } else
            editor.putString("answerKey", questionAns);

        if ((!fName.equals("") && !lName.equals("") && !email.equals("") && !mobile.equals("") && !pswrd.equals("")) && pswrd.equals(confPassword) && !question.equals("") && !questionAns.equals("")) {

            Toast.makeText(getApplicationContext(), "Successfully account created", Toast.LENGTH_SHORT).show();
            boolean flag=editor.commit();
            if(flag==true)
                startActivity(new Intent(this,SignInActivity.class));
        }
        else
        {
            confirmPassword.setHint("Confirm Password Must match");
            confirmPassword.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

    }
}
