package com.example.UserAccount;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * UserDetails: ehc
 * Date: 16/6/14
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dashboard extends ListActivity {
    static final String[] features =new String[] { "Emails", "Contacts", "Messages"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ImageListAdapter(this));

    }

  @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Log.i("View: ", "View:" + v);
       // Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
        if(selectedValue.equals("Emails")){
            Intent intent = new Intent(Dashboard.this, ShowDetails.class);
            intent.putExtra("get", "emails");
            startActivity(intent);
        }
        if(selectedValue.equals("Contacts")){
            Intent intent = new Intent(Dashboard.this, ShowDetails.class);
            intent.putExtra("get", "contacts");
            startActivity(intent);
        }
        if(selectedValue.equals("Messages")){
            Intent intent = new Intent(Dashboard.this, ShowDetails.class);
            intent.putExtra("get", "messages");
            startActivity(intent);
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
        Dashboard.this.finish();
    }
    void exitToHome(){
        moveTaskToBack(true);
        Dashboard.this.finish();
    }
    /*public void getEmails(View v) {
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
    }*/

    /*public void logout(View v){
        SharedPreferences sharedpreferences = getSharedPreferences
                (SignInActivity.mylogin, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        moveTaskToBack(true);
        Dashboard.this.finish();
    }
    public void exit(View v){
        moveTaskToBack(true);
        Dashboard.this.finish();
    }*/
}
