package com.osaigbovo.journalapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Home {

    private String date;
    private String emoticon;
    private String entry;
    private String image;
    private String time;

    public Home() {
    }

    public Home(String date, String emoticon, String entry, String image, String time) {
        this.date = date;
        this.emoticon = emoticon;
        this.entry = entry;
        this.image = image;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("date", date);
        result.put("emoticon", emoticon);
        result.put("entry", entry);
        result.put("image", image);
        result.put("time", time);
        return result;
    }
}
