package edu.berkeley.cs160.qUp.netio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.berkeley.cs160.qUp.model.User;

/**
 * Purpose of Class:
 * <p/>
 * qUp ==> edu.berkeley.cs160.qUp.netio
 * Date: 12/8/13
 * Time: 8:58 PM
 * Version: 1.0
 */



public class UserListResponse implements RESTResponse {

    protected List<User> userList;
    private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.ENGLISH);

    @Override
    public void success(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer());

        Gson gson = gsonBuilder.create();
        JsonParser parser = new JsonParser();
        //Clean data format if using Java 6

        JsonObject meta = parser.parse(json).getAsJsonObject();

        JsonObject response = meta.getAsJsonObject("response");
        JsonArray users = response.getAsJsonArray("users");
        ArrayList<User> userArray = new ArrayList<User>();
        for (int i = 0; i < users.size(); i++) {
            User user = gson.fromJson(users.get(i), User.class);
            userArray.add(user);
        }
        userList = userArray;
    }

    @Override
    public void fail(Exception ex) {

    }
}
