package com.osaigbovo.journalapp.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.models.Home;
import com.osaigbovo.journalapp.utilities.GlideApp;

public class HomeViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnCreateContextMenuListener,
        PopupMenu.OnMenuItemClickListener {

    private final ImageView mImageViewEmotion;
    //private final ImageView mImageViewOverlay;
    private final TextView mTextViewDate;
    private final TextView mTextViewFeeling;
    private final TextView mTextViewEntry;
    private final TextView mTextViewTime;
    private final TextView mTextViewOption;

    private Home mHome;

    Context mContext;
    private HomeAdapter.OnItemSelectedListener listener;

    public HomeViewHolder(View itemView) {
        super(itemView);

        mImageViewEmotion = itemView.findViewById(R.id.image_emotion);
        mTextViewDate = itemView.findViewById(R.id.text_date);
        mTextViewFeeling = itemView.findViewById(R.id.text_feeling);
        mTextViewTime = itemView.findViewById(R.id.text_time);
        mTextViewEntry = itemView.findViewById(R.id.text_journal);
        mTextViewOption = itemView.findViewById(R.id.textViewOptions);

        //mImageViewOverlay = itemView.findViewById(R.id.image_overlay);



        /*mImageViewOverlay.setOnClickListener(this);
        mImageViewOverlay.setOnCreateContextMenuListener(this);*/
    }

    public void bind(Home home) {
        mTextViewDate.setText(home.getDate());
        mTextViewFeeling.setText(home.getEmoticon());
        mTextViewEntry.setText(home.getEntry());
        mTextViewTime.setText(home.getTime());

        String mStringImage = home.getImage();
        getImage(mStringImage);
        mHome = home;

        mTextViewOption.setOnClickListener(this);
        mTextViewOption.setOnCreateContextMenuListener(this);
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

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (listener != null) {
            listener.onSelected(mHome);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (listener != null) {
            //Home home = objects.get(getAdapterPosition());
            listener.onMenuAction(mHome, item);
        }
        return false;
    }

}
