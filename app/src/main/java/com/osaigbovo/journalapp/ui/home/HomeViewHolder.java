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
package com.osaigbovo.journalapp.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.models.Home;
import com.osaigbovo.journalapp.utilities.GlideApp;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    public final ImageView mImageViewEmotion;
    private final TextView mTextViewDate;
    private final TextView mTextViewFeeling;
    private final TextView mTextViewEntry;
    private final TextView mTextViewTime;
    public final TextView mTextViewOption;

    Context mContext;

    public HomeViewHolder(View itemView) {
        super(itemView);

        mImageViewEmotion = itemView.findViewById(R.id.image_emotion);
        mTextViewDate = itemView.findViewById(R.id.text_date);
        mTextViewFeeling = itemView.findViewById(R.id.text_feeling);
        mTextViewTime = itemView.findViewById(R.id.text_time);
        mTextViewEntry = itemView.findViewById(R.id.text_journal);
        mTextViewOption = itemView.findViewById(R.id.textViewOptions);
    }

    public void bind(Home home) {
        mTextViewDate.setText(home.getDate());
        mTextViewFeeling.setText(home.getEmoticon());
        mTextViewEntry.setText(home.getEntry());
        mTextViewTime.setText(home.getTime());

        String mStringImage = home.getImage();
        getImage(mStringImage);
    }

    private void getImage(String mStringImage) {
        Resources resources = mImageViewEmotion.getContext().getResources();
        Drawable drawable;

        switch (mStringImage) {
            case "R.drawable.ic_laugh_selecteda.png":
                drawable = resources.getDrawable(R.drawable.ic_laugh_selecteda);
                displayIcon(drawable);
                break;
            case "R.drawable.ic_happy_selecteda.png":
                drawable = resources.getDrawable(R.drawable.ic_happy_selecteda);
                displayIcon(drawable);
                break;
            case "R.drawable.ic_meh_selecteda.png":
                drawable = resources.getDrawable(R.drawable.ic_meh_selecteda);
                displayIcon(drawable);
                break;
            case "R.drawable.ic_sad_selecteda.png":
                drawable = resources.getDrawable(R.drawable.ic_sad_selecteda);
                displayIcon(drawable);
                break;
            case "R.drawable.ic_crying_selecteda.png":
                drawable = resources.getDrawable(R.drawable.ic_crying_selecteda);
                displayIcon(drawable);
                break;
        }
    }

    private void displayIcon(Drawable drawable) {
        mContext = mImageViewEmotion.getContext();
        ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(mContext, R.color.colorAccent));

        GlideApp.with(mContext)
                .asDrawable()
                .circleCrop()
                .placeholder(cd)
                .load(drawable)
                .into(mImageViewEmotion);
    }
}
