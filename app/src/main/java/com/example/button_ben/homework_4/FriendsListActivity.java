package com.example.button_ben.homework_4;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class FriendsListActivity extends ListActivity {
    private ArrayList<String> friends_list = new ArrayList<String>();
    private ArrayList<String> friends_ids_list = new ArrayList<String>();
    private Button mAddButton;
    private Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        mAddButton = (Button) findViewById(R.id.add_friend_button);
        mLogoutButton = (Button) findViewById(R.id.logout_button);

        ParseQuery<ParseUser> query = ParseQuery.getQuery("Friend");
        query.whereEqualTo("me", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friend_list, com.parse.ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (int i = 0; i < friend_list.size(); i++) {
                        friends_list.add(friend_list.get(i).getUsername().toString());
                        friends_ids_list.add(friend_list.get(i).getUsername());
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading friends list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        ArrayAdapter namesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item_user, friends_list);
        setListAdapter(namesArrayAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsListActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.friend_string, friends_list);
                startActivity(intent);
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //allows logout
                ParseUser.logOut();
                Intent intent1 = new Intent(FriendsListActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        //redirects people to details page
        Intent details= new Intent(FriendsListActivity.this, FriendsDetailsActivity.class);
        details.putExtra(FriendsDetailsActivity.details_string, friends_ids_list.get(position));

        startActivity(details);

    }
}

