package edu.berkeley.cs160.qUp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Part of the qUp codebase.
 * <p/>
 * We are using
 * Created by sidneyfeygin on 12/5/13.
 */
public class Queue implements Comparable<Queue> {


    public Business business;
    public int id;
    @SerializedName("resource_uri")
    public String resourceURI;
    @SerializedName("time_entered_in_queue")
    public Date timeEnteredInQueue;
    public User user;

    public Queue() {
    }

    public Queue(Business business, int id, String resourceURI, Date timeEnteredInQueue, User user) {

        this.business = business;
        this.id = id;
        this.resourceURI = resourceURI;
        this.timeEnteredInQueue = timeEnteredInQueue;
        this.user = user;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public Date getTimeEnteredInQueue() {
        return timeEnteredInQueue;
    }

    public void setTimeEnteredInQueue(Date timeEnteredInQueue) {
        this.timeEnteredInQueue = timeEnteredInQueue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserString() {


        return user.toString();
    }


    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Queue another) {
        return another.getTimeEnteredInQueue().compareTo(timeEnteredInQueue);
    }


}
