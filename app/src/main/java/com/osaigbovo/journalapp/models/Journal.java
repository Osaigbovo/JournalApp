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

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Journal {

    public String stringDate, stringTime, stringEntry, stringImage, stringEmotion;
    //public Map<String, Boolean> stars = new HashMap<>();

    public Journal() {
        // Default constructor required for calls to DataSnapshot.getValue(Journal.class)
    }

    public Journal(String stringDate, String stringTime,
                   String stringEntry, String stringImage, String stringEmotion) {
        this.stringDate = stringDate;
        this.stringTime = stringTime;
        this.stringEntry = stringEntry;
        this.stringImage = stringImage;
        this.stringEmotion = stringEmotion;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", stringDate);
        result.put("time", stringTime);
        result.put("entry", stringEntry);
        result.put("image", stringImage);
        result.put("emoticon", stringEmotion);

        return result;
    }
}
