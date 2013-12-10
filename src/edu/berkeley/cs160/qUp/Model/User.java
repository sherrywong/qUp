package edu.berkeley.cs160.qUp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/5/13.
 */

public class User {

    public String email;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("id")
    public int userID;
    @SerializedName("is_business")
    public Boolean isBusiness;
    @SerializedName("is_premium")
    public Boolean isPremium;
    @SerializedName("last_login")
    public Date lastLogin;

    public String username;


    public User() {

    }

    public User(String email, String firstName, String lastName, int userID, Boolean isBusiness, Boolean isPremium, Date lastLogin, String username) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.isBusiness = isBusiness;
        this.isPremium = isPremium;
        this.lastLogin = lastLogin;
        this.username = username;
    }

    public User(String user) {

    }

    @Override
    public String toString() {
        return "User{" +

                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Gets username.
     *
     * @return Value of username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets userID.
     *
     * @return Value of userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets lastName.
     *
     * @return Value of lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email.
     *
     * @return Value of email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets new lastName.
     *
     * @param lastName New value of lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets new username.
     *
     * @param username New value of username.
     */
    public void setUsername(String username) {
        this.username = username;
    }



    /**
     * Sets new isPremium.
     *
     * @param isPremium New value of isPremium.
     */
    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     * Gets isBusiness.
     *
     * @return Value of isBusiness.
     */
    public Boolean getIsBusiness() {
        return isBusiness;
    }

    /**
     * Sets new userID.
     *
     * @param userID New value of userID.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets firstName.
     *
     * @return Value of firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets isPremium.
     *
     * @return Value of isPremium.
     */
    public Boolean getIsPremium() {
        return isPremium;
    }

    /**
     * Sets new lastLogin.
     *
     * @param lastLogin New value of lastLogin.
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Sets new email.
     *
     * @param email New value of email.
     */
    public void setEmail(String email) {
        this.email = email;
    }



    /**
     * Gets lastLogin.
     *
     * @return Value of lastLogin.
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets new firstName.
     *
     * @param firstName New value of firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets new isBusiness.
     *
     * @param isBusiness New value of isBusiness.
     */
    public void setIsBusiness(Boolean isBusiness) {
        this.isBusiness = isBusiness;
    }
}
