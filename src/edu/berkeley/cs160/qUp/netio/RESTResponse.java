package edu.berkeley.cs160.qUp.netio;

/**
 * Part of the qUp codebase.
 * <p/>
 * Lifted from GitHub: kylewbanks.com-AndroidApp
 * Created by sidneyfeygin on 12/5/13.
 */
public interface RESTResponse {

    /**
     * Called upon successful completion of a HTTP request with the JSON that the server responded with.
     *
     * @param json
     */
    void success(String json);

    /**
     * Called if the HTTP request fails for any reason.
     *
     * @param ex
     */
    void fail(Exception ex);

}
