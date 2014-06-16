package com.example.UserAccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 16/6/14
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowDetails extends Activity {
    String email;
    String contacts;
    String messages;
    TextView outputText;

    private ListView listView;
    private List<ContactBean> list = new ArrayList<ContactBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.show_details);


        if (getIntent().getExtras().getString("emails").equals("emails")) {
            getEmails();

        }
        if (getIntent().getExtras().getString("contacts").equals("contacts")) {
            getContacts();

        }
        if (getIntent().getExtras().getString("messages").equals("messages")) {
            getMessages();

        }
    }


    public void getContacts() {


    }


    public void getMessages() {


    }

    public void getEmails() {


    }

}







