package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Andy on 10/13/2016.
 */

@RunWith(AndroidJUnit4.class)
public class TestAsyncJoke {

    private static final String TAG = TestAsyncJoke.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testAsyncFetchJoke() throws Exception {
        //onView(withId(R.id.btn_tell_joke)).perform(click());
        final Object syncObject = new Object();

        MainActivity testAsyncActivity = mActivityRule.getActivity();
        testAsyncActivity.executeAsyncTask();


        testAsyncActivity.setTestSyncCallback(new MainActivity.TestSyncCallback(){

            @Override
            public void onReturnJoke(String joke) {
                final String jokeTested = "This joke source is so funny hahaha";
                Log.d("TESTASSERT" , ">> joke: " + joke);
                assertTrue(jokeTested.equals(joke));
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        });

        /*
         * Here is a synchronization on local variable but it's ok.
         */
        synchronized (syncObject){
            syncObject.wait();
        }
    }
}