package edu.berkeley.cs160.qUp.netio;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Part of the qUp codebase.
 * <p/>
 * Lifted from GitHub: kylewbanks.com-AndroidApp
 * Created by sidneyfeygin on 12/5/13.
 */

public class RESTController {

    public static final String QUEUE_EP_URL = "http://cs160-qup.herokuapp.com/api/v1/queues";
    public static final String USER_EP_URL = "http://cs160-qup.herokuapp.com/api/v1/users";

    public static final String TAG = "ParsingActivity";

    /**
     * Fetches a list of Posts from the remote server.
     *
     * @param response
     */
    public static void retrieveQueueList(QueueListResponse response, long[] knownQIds) {
        Log.i(TAG, "Loading QUEUE List...");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("knownQIds", Arrays.toString(knownQIds));

        RESTPerformer restPerformer = new RESTPerformer();
        restPerformer.execute(getPackedParameters("", params, response));
    }

    /**
     * Fetches a list of Queue from the remote server.
     *
     * @param response
     */
    public static void retrieveUserList(UserListResponse response, String username, String password, String serverUrl) {

        Log.i(TAG, "Loading USER List...");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Username", username);
        params.put("Password", password);

        RESTPerformer restPerformer = new RESTPerformer();

        restPerformer.execute(getPackedParameters(serverUrl, params, response));
    }



    /**
     * Bundles any additional parameters you want to pass to the server with the authentication parameters.
     * Also adds the required RESTResponse callback class, and server URL to the dictionary.
     */
    private static HashMap<String, Object> getPackedParameters(String serverURL, Map<String, Object> additionalParams, RESTResponse response) {
        HashMap<String, Object> packedParams = new HashMap<String, Object>();

        if (!serverURL.startsWith("/")) {
            serverURL = "" + serverURL;
        }
        packedParams.put(RESTPerformer.SERVER_URL, QUEUE_EP_URL + serverURL);

        if (additionalParams != null) {
            packedParams.putAll(additionalParams);
        }
        packedParams.put(RESTPerformer.CALLBACK_CLASS, response);

        return packedParams;
    }

    private static class RESTPerformer extends AsyncTask<HashMap<String, Object>, Void, String> {

        private static final String TAG = "RESTPerformer";
        public static final String CALLBACK_CLASS = "CallbackClass";
        public static final String SERVER_URL = "ServerURL";
        private static final String POST_REQUEST = "PostRequest";
        public static final String USERNAME = "Username";
        public static final String PASSWORD = "Password";

        /**
         * Takes and InputStream and reads it's contents into a String.
         *
         * @param is
         * @return
         */
        private static String convertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        /**
         * Performs the actual HTTP request, in a background thread.
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(HashMap<String, Object>... params) {
            //Retrieve the callback class and remove it from the map
            RESTResponse callback;
            if (params[0].containsKey(CALLBACK_CLASS)) {
                callback = (RESTResponse) params[0].get(CALLBACK_CLASS);
                params[0].remove(CALLBACK_CLASS);
            } else {
                callback = new RESTResponse() {
                    @Override
                    public void success(String json) {
                        Log.e(TAG, "Callback not implemented!");
                    }

                    @Override
                    public void fail(Exception ex) {
                        Log.e(TAG, "Callback not implemented!");
                    }
                };
            }

            //Retrieve the Server URL and remove it from the map
            String serverURL;
            if (params[0].containsKey(SERVER_URL)) {
                serverURL = params[0].get(SERVER_URL).toString();
                params[0].remove(SERVER_URL);
            } else {
                Log.e(TAG, "No server URL provided.");
                callback.fail(new Exception("No server URL provided"));
                return null;
            }

            //Generate the POST parameters
            StringBuilder builder = new StringBuilder(serverURL + "?");
            MultipartEntity mpEntity = new MultipartEntity();
            try {
                for (Map.Entry<String, Object> entry : params[0].entrySet()) {
                    builder.append(entry.getKey() + "=" + entry.getValue() + "&");
                    mpEntity.addPart(entry.getKey(), new StringBody(entry.getValue().toString()));
                }
            } catch (Exception ex) {
                callback.fail(ex);
                return null;
            }
            HttpResponse response;
            try {

                Log.i(TAG, "Requesting: " + builder.toString());
                HttpClient client = new DefaultHttpClient();
                if (params[0].containsKey(USERNAME)) {
                    String un = (String) params[0].get(USERNAME);
                    String pass = (String) params[0].get(PASSWORD);
                    HttpGet request = new HttpGet(USER_EP_URL);
                    String authorizationString = "Basic " + Base64.encodeToString((un + ":" + pass).getBytes(), Base64.DEFAULT);
                    request.setHeader("Authorization", authorizationString.replace("\n", ""));
                    response = client.execute(request);


                } else {
                    HttpGet request = new HttpGet(QUEUE_EP_URL);
                    response = client.execute(request);
                }

                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        callback.success(RESTPerformer.convertStreamToString(content));
                    } catch (Exception ex) {
                        Log.e(TAG, "Failed to parse JSON due to: " + ex);
                        callback.fail(ex);
                    }
                    content.close();
                } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    callback.fail(new Exception("Server responded with response code: " + statusLine.getStatusCode()));
                }
            } catch (Exception ex) {
                Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
                callback.fail(ex);
            }
            return null;
        }

    }

}

