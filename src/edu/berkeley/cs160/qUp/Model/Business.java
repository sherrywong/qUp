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




    @Override
    public String toString() {
        return name;
    }


    /**
     * Sets new businessId.
     *
     * @param businessId New value of businessId.
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     * Sets new avgWaitTime.
     *
     * @param avgWaitTime New value of avgWaitTime.
     */
    public void setAvgWaitTime(float avgWaitTime) {
        this.avgWaitTime = avgWaitTime;
    }

    /**
     * Sets new resourceURI.
     *
     * @param resourceURI New value of resourceURI.
     */
    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    /**
     * Gets lat.
     *
     * @return Value of lat.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Gets businessId.
     *
     * @return Value of businessId.
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * Sets new lon.
     *
     * @param lon New value of lon.
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Gets resourceURI.
     *
     * @return Value of resourceURI.
     */
    public String getResourceURI() {
        return resourceURI;
    }



    /**
     * Sets new lat.
     *
     * @param lat New value of lat.
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Sets new organizationType.
     *
     * @param organizationType New value of organizationType.
     */
    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    /**
     * Gets avgWaitTime.
     *
     * @return Value of avgWaitTime.
     */
    public float getAvgWaitTime() {
        return avgWaitTime;
    }

    /**
     * Gets organizationType.
     *
     * @return Value of organizationType.
     */
    public String getOrganizationType() {
        return organizationType;
    }

    /**
     * Sets new name.
     *
     * @param name New value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets lon.
     *
     * @return Value of lon.
     */
    public double getLon() {
        return lon;
    }
}
