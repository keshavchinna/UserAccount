package com.example.UserAccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * UserDetails: ehc
 * Date: 16/6/14
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckSecurityQuestion extends Activity {
    private TextView showQuestion, questionErrorMessage;
    private EditText answer;
    private Button sendMessage;
    private String questionAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.check_security_question);
        showQuestion = (TextView) findViewById(R.id.displayQuestion);
        answer = (EditText) findViewById(R.id.questionAnswer);
        questionErrorMessage = (TextView) findViewById(R.id.questionErrorMessage);
        sendMessage = (Button) findViewById(R.id.sendMessage);
        showQuestion.setText(getIntent().getExtras().getString("question"));
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionAnswer = answer.getText().toString();
                Log.d("Test", "answer: " + questionAnswer);
                if (getIntent().getExtras().getString("answer").equals(questionAnswer)) {
                    String sms = "your Password is " + getIntent().getExtras().getString("password");
                    Log.d("Test", "enteredAnswer: " + sms);
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(getIntent().getExtras().getString("mobile"), null, sms, null, null);

                        Toast.makeText(getApplicationContext(), "Password Successfully Sent to "+getIntent().getExtras().getString("mobile") +" !",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CheckSecurityQuestion.this,SignInActivity.class));

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                } else
                    questionErrorMessage.setText("Wrong Answer Try Again!");

            }
        });
    }
}
