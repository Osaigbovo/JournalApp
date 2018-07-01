package com.osaigbovo.journalapp.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.utilities.GlideApp;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    private final ImageView mImageViewEmotion;
    private final TextView mTextViewDate;
    private final TextView mTextViewFeeling;
    private final TextView mTextViewEntry;
    private final TextView mTextViewTime;
    private StringBuilder mStringBuilder;

    Context mContext;

    public HomeViewHolder(View itemView) {
        super(itemView);

        mImageViewEmotion = itemView.findViewById(R.id.image_emotion);
        mTextViewDate = itemView.findViewById(R.id.text_date);
        mTextViewFeeling = itemView.findViewById(R.id.text_feeling);
        mTextViewTime = itemView.findViewById(R.id.text_time);
        mTextViewEntry = itemView.findViewById(R.id.text_journal);

        Log.w("JJJJJJJJ", "ggggggggggggggggggg");
    }

    public void bind(Home home) {
        mTextViewDate.setText(home.getDate());
        mTextViewFeeling.setText(home.getEmoticon());
        mTextViewEntry.setText(home.getEntry());
        mTextViewTime.setText(home.getTime());

        Log.w("JJJJJJJJ", home.getDate() + home.getEntry());

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
