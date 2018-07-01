/*
 * Copyright 2018.  Osaigbovo Odiase
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osaigbovo.journalapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Home implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.date);
        parcel.writeString(this.emoticon);
        parcel.writeString(this.entry);
        parcel.writeString(this.image);
        parcel.writeString(this.time);
    }

    protected Home(Parcel in) {
        this.date = in.readString();
        this.emoticon = in.readString();
        this.entry = in.readString();
        this.image = in.readString();
        this.time = in.readString();
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        public Home createFromParcel(Parcel source) {
            return new Home(source);
        }

        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

}
