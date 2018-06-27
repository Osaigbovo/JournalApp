package com.osaigbovo.journalapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.osaigbovo.journalapp.ui.calender.CalenderFragment;
import com.osaigbovo.journalapp.ui.home.HomeFragment;
import com.osaigbovo.journalapp.utilities.GlideApp;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener, CalenderFragment.OnFragmentInteractionListener {

    private BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (@NonNull MenuItem item) -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Fragment homeFragment = HomeFragment.newInstance();
                launchFragment(homeFragment);
                return true;
            case R.id.navigation_calender:
                Fragment calenderFragment = CalenderFragment.newInstance();
                launchFragment(calenderFragment);
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(getDrawable(R.drawable.ic_home_black_24dp));

        // TODO New > Image Asset > Action Bar and Tab Icons
        //getSupportActionBar().setIcon(getDrawable(R.drawable.ic_menu_camera));

        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Fragment homeFragment = HomeFragment.newInstance();

            setTitle("Home");

            launchFragment(homeFragment);
            // Selected item at app launch
            mBottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem userItem = menu.findItem(R.id.action_user);

        GlideApp
                .with(this)
                .asBitmap()
                .circleCrop()
                .placeholder(R.drawable.ic_round_account_circle_24px)
                .load(R.drawable.user_image)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        userItem.setIcon(new BitmapDrawable(getResources(), resource));
                    }
                });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_user) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // TODO Add Comments
    private void launchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

}
