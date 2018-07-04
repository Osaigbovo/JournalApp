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
package com.osaigbovo.journalapp;

import android.content.Intent;
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

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.osaigbovo.journalapp.ui.calender.CalenderFragment;
import com.osaigbovo.journalapp.ui.home.HomeFragment;
import com.osaigbovo.journalapp.ui.userinfo.MenuActivity;
import com.osaigbovo.journalapp.utilities.GlideApp;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener, CalenderFragment.OnFragmentInteractionListener, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
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
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Fragment homeFragment = HomeFragment.newInstance();

            setTitle("Home");

            launchFragment(homeFragment);
            // Selected item at app launch
            mBottomNavigationView.getMenu().getItem(0).setChecked(true);
        }

        user = mAuth.getCurrentUser();
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem userItem = menu.findItem(R.id.action_user);
        GlideApp
                .with(this)
                .asBitmap()
                .circleCrop()
                .placeholder(R.drawable.ic_round_account_circle_24px)
                .load(user.getPhotoUrl())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
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
        int id = item.getItemId();
        if (id == R.id.action_user) {
            Intent menuActivityIntent =
                    new Intent(MainActivity.this, MenuActivity.class);
            startActivity(menuActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
