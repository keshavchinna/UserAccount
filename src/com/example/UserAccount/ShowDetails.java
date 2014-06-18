package com.example.UserAccount;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    String email;
    String contacts;
    String messages;
    TextView outputText,textView;
    ListView lViewSMS ;
    ArrayList<String> contactList;
    String[] details;
    ArrayAdapter<String> adapter;
    StringBuffer contact;
    StringBuffer msgs;
    StringBuffer emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.show_details);
         lViewSMS = (ListView) findViewById(R.id.listViewSMS);
        outputText=(TextView)findViewById(R.id.showDetails);
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

            Log.d("Test","outside fetchbox()");
            Log.d("Test", "BufferSize: " + msgs.length());
            //outputText.setText(msgs);
        }
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
    public void getEmails() {


    }

     ArrayList getMessagesFromInbox()
        {
        ArrayList sms = new ArrayList();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            String msgDate=convertDate(cursor.getString(2));
            Log.d("MSG", address);
            Log.d("MSG",body);
            msgs.append("From: ").append(address).append("\n").append("Date: ").append(msgDate).append("\n").append("Body: ").append("\n").append(body).append("\n").append("-------------------").append("\n");
            /*System.out.println("======&gt; Mobile number =&gt; "+address);
            System.out.println("=====&gt; SMS Text =&gt; "+body);*/
            sms.add("From: " + address + "\nDate: " + msgDate+"\nBody: \n"+body);
        }
        return sms;

    }
    public ArrayList<String> getNameEmailDetails(){
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
        String dateMilliSeconds =date;
        DateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        long milliSeconds= Long.parseLong(dateMilliSeconds);
         Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Log.d("Date","Date: "+formatter.format(calendar.getTime()));
        return formatter.format(calendar.getTime());
    }
}







