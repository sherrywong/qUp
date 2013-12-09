package edu.berkeley.cs160.qUp.netio;

import com.google.gson.*;
import edu.berkeley.cs160.qUp.Model.Queue;
import edu.berkeley.cs160.qUp.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public void success(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        JsonParser parser = new JsonParser();
        //Clean data format if using Java 6

        JsonObject meta = parser.parse(json).getAsJsonObject();

        JsonObject response = meta.getAsJsonObject("response");
        JsonArray users = response.getAsJsonArray("queues");
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
