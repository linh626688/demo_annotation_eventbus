package com.helix.test_androidannotation_eventbus;

/**
 * Created by Giang Hoang on 23/06/2016.
 */
public class Message {
    public final int percent;
    public final int follow;

    public Message(int percent, int follow) {
        this.percent = percent;
        this.follow = follow;
    }
}
