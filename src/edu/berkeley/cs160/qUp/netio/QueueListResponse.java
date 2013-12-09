package edu.berkeley.cs160.qUp.netio;

import com.google.gson.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.berkeley.cs160.qUp.Model.Queue;
import org.json.JSONObject;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/6/13.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QueueListResponse implements RESTResponse {

    protected List<Queue> queueList;

    /**
     * Called when the Queue List is successfully loaded from the remote server to deserialize it from JSON to Queue objects.
     *
     * @param json
     */
    @Override
    public void success(String json) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer());
        Gson gson = gsonBuilder.create();
        JsonParser parser = new JsonParser();
        //Clean data format if using Java 6

        JsonObject meta = parser.parse(json).getAsJsonObject();

        JsonObject response = meta.getAsJsonObject("response");
        JsonArray queues = response.getAsJsonArray("queues");
        ArrayList<Queue> queueArray = new ArrayList<Queue>();
        for (int i = 0; i < queues.size(); i++) {

            Queue queue = gson.fromJson(queues.get(i), Queue.class);
            queueArray.add(queue);

        }
        queueList = queueArray;

    }

    @Override
    public void fail(Exception ex) {

    }




}
