package com.mohamed14riad.rssnewsreader.data.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Feed {
    @Element(name = "channel")
    private Channel channel;

    public Feed() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
