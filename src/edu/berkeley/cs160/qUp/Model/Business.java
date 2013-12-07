package edu.berkeley.cs160.qUp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/5/13.
 */
public class Business {

    public Business() {
    }

    @SerializedName("avg_wait_time")
    public float avgWaitTime;

    @SerializedName("business_id")
    public int businessId;

    public double lat;

    public double lon;

    public String name;

    @SerializedName("organization_type")
    public String organizationType;

    @SerializedName("resource_uri")
    public String resourceURI;

    @SerializedName("user")
    public User user;

    @Override
    public String toString() {
        return name + '\'' +
                " is located at: (lat:" + lat +
                ", lon:" + lon +
                "), and is a(n)'" + organizationType + '\'' +
                ", which is owned by " + user +
                '}' + ". The average wait time is: " + avgWaitTime + ".";
    }
}
