package edu.berkeley.cs160.qUp.DataManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/6/13.
 */
public class QueueResponse implements RESTResponse {

    protected List<QueueResponse> queueResponseList;

    /**
     * Called when the Queue List is successfully loaded from the remote server to deserialize it from JSON to Queue objects.
     *
     * @param json
     */
    @Override
    public void success(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();

        queueResponseList = Arrays.asList(gson.fromJson(json, QueueResponse[].class));
    }

    @Override
    public void fail(Exception ex) {

    }
}
