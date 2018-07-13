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
package com.osaigbovo.journalapp.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.osaigbovo.journalapp.R;
import com.osaigbovo.journalapp.ui.login.GoogleSignInActivity;
import com.osaigbovo.journalapp.utilities.GlideApp;

public class MenuActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private ImageView mUserImage;
    private TextView mUserText;
    private TextView mUserEmail;
    private Button mSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        mUserImage = findViewById(R.id.image_user);
        mUserText = findViewById(R.id.text_user);
        mUserEmail = findViewById(R.id.text_email);

        displayInfo(user);

        mSignOut = findViewById(R.id.button_signout);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                Intent signInIntent
                        = new Intent(MenuActivity.this, GoogleSignInActivity.class);
                startActivity(signInIntent);
                finish();
            }
        });
    }

    private void displayInfo(FirebaseUser user) {
        mUserText.setText(user.getDisplayName());
        mUserEmail.setText(user.getEmail());

        GlideApp
                .with(this)
                .asBitmap()
                .circleCrop()
                .placeholder(R.drawable.ic_round_account_circle_24px)
                .load(user.getPhotoUrl())
                .into(mUserImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("User");
    }
}
