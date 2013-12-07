package edu.berkeley.cs160.qUp.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/5/13.
 */
public class User {
    public String email;
    public String firstName;
    public String lastName;
    public int userID;
    public Boolean isBusiness;
    public Boolean isPremium;
    public Date lastLogin;
    public String username;
    public ArrayList<Queue> queueList = new ArrayList<Queue>();

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
