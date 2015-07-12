package com.example.button_ben.homework_4;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    public static final String friend_string = "com.example.button_ben.homework_4.friend_string";
    private ArrayList<String> other_user_ids = new ArrayList<String>();
    private ArrayList<String> other_users = new ArrayList<String>();
    private ArrayList<String> friends_list = new ArrayList<String>();
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String currentUserId = ParseUser.getCurrentUser().getObjectId();

        mBackButton = (Button) findViewById(R.id.back_button_2);

        Intent i = getIntent();
        friends_list = i.getStringArrayListExtra(friend_string);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        //don't include yourself or friends
        query.whereNotEqualTo("objectId", currentUserId);
        for (int r = 0; r<friends_list.size();r++) {
            query.whereNotEqualTo("username", friends_list.get(r));
        }
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        other_users.add(userList.get(i).getUsername().toString());
                        other_user_ids.add(userList.get(i).getUsername());
                    }

                    ArrayAdapter namesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item_user, other_users);
                    setListAdapter(namesArrayAdapter);

                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent details= new Intent(MainActivity.this, UserDetailsActivity.class);
        details.putExtra(UserDetailsActivity.details_string, other_user_ids.get(position));

        startActivity(details);

    }
}
