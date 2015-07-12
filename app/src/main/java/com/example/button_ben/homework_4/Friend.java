package com.example.button_ben.homework_4;

/**
 * Created by Button_ben on 7/12/2015.
 */
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Friend")
public class Friend extends ParseObject {
    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public String getFriendId() {
        return getString("userId");
    }

    public void setFriendId(String userId) {
        put("userId", userId);
    }

}