package edu.berkeley.cs160.qUp.netio;

/**
 * Part of the qUp codebase.
 * Lifted from Github: kylewbanks.com-AndroidApp
 */

import java.util.ArrayList;
import java.util.List;

import edu.berkeley.cs160.qUp.Model.Queue;

/**
 * Created by kylewbanks on 2013-10-09.
 */
public interface QueueListUpdateListener {

    /**
     * Called when the Post list is made available, or has been updated
     *
     * @param queues An array list of queues
     */
    void onQueueListLoaded(List<Queue> queues);

}
