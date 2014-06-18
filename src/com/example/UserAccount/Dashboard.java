package com.example.UserAccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * UserDetails: ehc
 * Date: 16/6/14
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dashboard extends Activity {

    Button emails;
    Button contacts;
    Button messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.dashboard);
        emails = (Button) findViewById(R.id.emails);
        contacts = (Button) findViewById(R.id.contacts);
        messages = (Button) findViewById(R.id.messages);
    }

    public void getEmails(View v) {
        Intent intent = new Intent(Dashboard.this, ShowDetails.class);
        intent.putExtra("get", "emails");
        startActivity(intent);
    }

    public void getContacts(View v) {
        Intent intent = new Intent(Dashboard.this, ShowDetails.class);
        intent.putExtra("get", "contacts");
        startActivity(intent);
    }

    public void getMessages(View v) {
        Intent intent = new Intent(Dashboard.this, ShowDetails.class);
        intent.putExtra("get", "messages");
        startActivity(intent);
    }
}
