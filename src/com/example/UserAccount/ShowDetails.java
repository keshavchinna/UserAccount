package com.example.UserAccount;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * UserDetails: ehc
 * Date: 16/6/14
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowDetails extends Activity {

    private ListView lViewSMS ;
    private ArrayList<String> contactList;
    private StringBuffer contact;
    private StringBuffer msgs;
    private StringBuffer emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.show_details);
         lViewSMS = (ListView) findViewById(R.id.listViewSMS);
         Log.d("Test","welcome");
        contact=new StringBuffer();
        msgs=new StringBuffer();
        emails=new StringBuffer();
        if (getIntent().getExtras().getString("get").equals("emails")) {

            ArrayList<String> names=getNameEmailDetails();
            Log.d("Test","emailsSize: "+emails.length());
            Object[] contactEmails=names.toArray();
            if(contactEmails.length>0)
            {
                ArrayAdapter adapter=new ArrayAdapter(this,R.layout.show_details,R.id.showDetails,contactEmails);
                lViewSMS.setAdapter(adapter);
                //outputText.setText(emails);
            }
            else
                Toast.makeText(getApplicationContext(),"No Emails Exist",Toast.LENGTH_LONG).show();
        }
        if (getIntent().getExtras().getString("get").equals("contacts")) {
              Log.d("Test","checking");

            contactList=readContacts();
            Object[] contacts=contactList.toArray();
            if(contacts.length>0){
                ArrayAdapter adapter=new ArrayAdapter(this,R.layout.show_details,R.id.showDetails,contacts);
                lViewSMS.setAdapter(adapter);
                lViewSMS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    /*Toast toast=Toast.makeText(getApplicationContext(), "Hello welcome", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 40, 30);
                        toast.show();*/
                     }
                });
                //outputText.setText(contact);
            }
            else
                Toast.makeText(getApplicationContext(),"No Contacts Exist",Toast.LENGTH_LONG).show();
         }
        if (getIntent().getExtras().getString("get").equals("messages")) {
            Log.d("Test","Inside getMessages");
            ArrayList list=getMessagesFromInbox();
            Log.i("Test","converting list to array");
            Object[] listValues=list.toArray();
            Log.d("Test","ArrayList Size: "+list.size());
            Log.d("Test", "String array Size: " + listValues.length);
            if(listValues.length>0)
            {
                Log.d("Test","inside fetchbox()");
                ArrayAdapter adapter = new ArrayAdapter(this, R.layout.show_details,R.id.showDetails,listValues);
                Log.d("Test","listadapter is adding");
                lViewSMS.setAdapter(adapter);
                Log.d("Test","listadapter is added");
            }else
                Toast.makeText(getApplicationContext(),"No Messages Exist",Toast.LENGTH_LONG).show();
              lViewSMS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      //To change body of implemented methods use File | Settings | File Templates.

                  }
              });
            Log.d("Test","outside fetchbox()");
            Log.d("Test", "BufferSize: " + msgs.length());
            //outputText.setText(msgs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout:
                signOut();
                return true;
            case R.id.action_home:
                exitToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

     void signOut(){
        SharedPreferences sharedpreferences = getSharedPreferences
                (SignInActivity.mylogin, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        moveTaskToBack(true);
        ShowDetails.this.finish();
    }
     void exitToHome(){
        moveTaskToBack(true);
        ShowDetails.this.finish();
    }
    ArrayList<String> readContacts() {

        ArrayList<String> resultSet=new ArrayList<String>();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d("data","Name: "+name+"   Number: "+phoneNumber);
            resultSet.add(name+"\n"+phoneNumber);
            contact.append(name).append("\n").append(phoneNumber).append("\n").append("--------------------").append("\n");
        }
        Log.d("Test","outside of while loop");

         return resultSet;
    }


     ArrayList getMessagesFromInbox()
        {
        ArrayList sms = new ArrayList();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body","type"," service_center","person"},null,null,null);

        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String id=cursor.getString(0);
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            String msgDate=convertDate(cursor.getString(2));
            String type=cursor.getString(4);
            String serviceCenter=cursor.getString(5);
            String person=cursor.getString(6);

            Log.d("MSG","ID: "+id);
            Log.d("MSG", "address: "+address);
            Log.d("MSG","body"+body);
            Log.d("MSG","Type: "+type);
            Log.d("MSG","serviceCenter: "+serviceCenter);
            Log.d("MSG","person: "+person);

            msgs.append("From: ").append(address).append("\n").append("Date: ").append(msgDate).append("\n").append("Body: ").append("\n").append(body).append("\n").append("-------------------").append("\n");

            sms.add("From: " + address + "\nDate: " + msgDate+"\nBody: \n"+body);
        }
        return sms;

    }
     ArrayList<String> getNameEmailDetails(){
        ArrayList<String> names = new ArrayList<String>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
                    String name=cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.d("Name :", "Name: "+name);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.d("Email", "Email: "+email);
                    if(email!=null){
                        names.add(name+"\n"+email);
                        emails.append("Name: ").append(name).append("\n").append("Email-Id: ").append(email).append("\n").append("------------------------------").append("\n");
                    }
                }
                cur1.close();
            }
        }
        return names;
    }

    String convertDate(String date)
    {

        DateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        long milliSeconds= Long.parseLong(date);
         Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Log.d("Date","Date: "+formatter.format(calendar.getTime()));
        return formatter.format(calendar.getTime());
    }
}







