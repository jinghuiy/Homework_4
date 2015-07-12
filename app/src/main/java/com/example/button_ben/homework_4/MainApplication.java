package com.example.button_ben.homework_4;

import android.app.Application;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models here
        //ParseObject.registerSubclass(Message.class);

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "p4q1YirUKcUkAclykAX5JlJhVa33farM1gdH123Z", "wcnWpucF7hGHQGo6n6YDGcfgppomakK430sasjBs");


    }
}