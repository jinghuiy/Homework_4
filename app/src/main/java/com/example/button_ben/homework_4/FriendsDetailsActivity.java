package com.example.button_ben.homework_4;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class FriendsDetailsActivity extends ActionBarActivity {

    public static final String details_string="com.example.button_ben.homework_4.details_string";
    private String user_id;
    private Button mBackButton;
    private Button mRemoveButton;
    private TextView mUsernameText;
    private ParseUser current_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Intent i = getIntent();
        user_id = i.getStringExtra(details_string);

        mBackButton = (Button) findViewById(R.id.back_button3);
        mRemoveButton = (Button) findViewById(R.id.remove_friend_button);
        mUsernameText = (TextView) findViewById(R.id.username_text2);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId",user_id);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, com.parse.ParseException e) {
                if (e == null) {
                    // The query was successful.
                    mUsernameText.setText("Username: " + objects.get(0).getUsername().toString());
                    current_friend = objects.get(0);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user details",
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

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject friend = new ParseObject("Friend");
                friend.put("me", ParseUser.getCurrentUser());
                friend.put("friend", current_friend);
                friend.deleteInBackground();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
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
}
