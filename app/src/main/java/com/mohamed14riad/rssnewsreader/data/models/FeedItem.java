package com.mohamed14riad.rssnewsreader.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Entity
@Root(name = "item", strict = false)
public class FeedItem implements Parcelable {
    @Element(name = "title")
    private String title;
    @Element(name = "link")
    @PrimaryKey
    @NonNull
    private String link;
    @Element(name = "description")
    private String description;
    @Element(name = "pubDate")
    private String pubDate;

    @Ignore
    public FeedItem() {
    }

    public FeedItem(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeString(this.pubDate);
    }

    private FeedItem(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        this.description = in.readString();
        this.pubDate = in.readString();
    }

    public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel source) {
            return new FeedItem(source);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
