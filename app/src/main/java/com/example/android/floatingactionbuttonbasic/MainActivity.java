/*
* Copyright 2013 The Android Open Source Project
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




package com.example.android.floatingactionbuttonbasic;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase implements View.OnClickListener, FabView.OnCheckedChangeListener {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    Drawable mDrawable;
    ImageView mFabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT < 21) {
            setContentView(R.layout.activity_backward);
            mFabView = (FabViewBackward) findViewById(R.id.fab_view_details);
            mFabView.setOnClickListener(this);
            ((FabViewBackward)mFabView).setOnCheckedChangeListener(this);
            mDrawable = null;
        } else {
            setContentView(R.layout.activity_main);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FloatingActionButtonBasicFragment fragment = new FloatingActionButtonBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
            mFabView = (FabView) findViewById(R.id.fab_view_details);
            //mFabView.setTransitionName(SHARED_FAB_VIEW);
            mFabView.setOnClickListener(this);
            mDrawable = ((FabView)mFabView).getCDrawable();
            ((FabView)mFabView).setOnCheckedChangeListener(this);
        }



    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        //this activity is click listening to only one object
        if (mFabView.isSelected()) {

            if(mDrawable != null) {
                mFabView.setSelected(true);
                mDrawable.jumpToCurrentState();
                ((FabView)mFabView).setChecked(false);
                mFabView.setSelected(false);
            } else {
                ((FabViewBackward)mFabView).setChecked(false);
                mFabView.setSelected(false);
            }


        } else {
            if(mDrawable != null) {
                mFabView.setSelected(false);
                mDrawable.jumpToCurrentState();
                ((FabView)mFabView).setChecked(true);
                mFabView.setSelected(true);
            } else {
                mFabView.setSelected(true);
                ((FabViewBackward)mFabView).setChecked(true);
            }


        }
        Log.d(TAG, "FabView tap captured.\n");
    }

    @Override
    public void onCheckedChanged(FabView fabView, boolean isChecked) {
        Log.d(TAG, String.format("FabView was %s.\n", isChecked ? "checked" : "unchecked"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Create a chain of targets that will receive log data */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
}
